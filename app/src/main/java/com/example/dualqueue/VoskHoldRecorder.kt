package com.example.dualqueue

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Function

class VoskHoldRecorder(
    private val activity: Activity,
    private val holdButton: Button,
    private val input: EditText,
    private val status: TextView,
    private val normalizer: Function<String, String>,
    private val onCommitted: Runnable
) : RecognitionListener {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val mainHandler = Handler(Looper.getMainLooper())
    private val committed = AtomicBoolean(false)

    @Volatile private var model: Model? = null
    @Volatile private var modelLoading = true

    private var recognizer: Recognizer? = null
    private var speechService: SpeechService? = null
    private var holding = false
    private var lastPartial = ""
    private var lastResult = ""

    init {
        bindHoldGesture()
        loadModelAsync()
    }

    private fun bindHoldGesture() {
        holdButton.setOnClickListener(null)
        holdButton.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    view.parent?.requestDisallowInterceptTouchEvent(true)
                    holding = true
                    committed.set(false)
                    lastPartial = ""
                    lastResult = ""
                    holdButton.isPressed = true
                    holdButton.text = "🔴 Recording — lift to send"

                    if (!hasAudioPermission()) {
                        holding = false
                        holdButton.isPressed = false
                        holdButton.text = "🎙  Hold to speak"
                        activity.requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)
                        return@setOnTouchListener true
                    }

                    val readyModel = model
                    if (readyModel == null) {
                        holding = false
                        holdButton.isPressed = false
                        holdButton.text = "🎙  Hold to speak"
                        status.text = if (modelLoading) "Voice model is still loading…" else "Voice model unavailable"
                        return@setOnTouchListener true
                    }

                    startListening(readyModel)
                    true
                }

                MotionEvent.ACTION_UP -> {
                    view.parent?.requestDisallowInterceptTouchEvent(false)
                    holding = false
                    holdButton.isPressed = false
                    holdButton.text = "⏳ Transcribing…"
                    stopAndShutdown(commit = true)
                    true
                }

                MotionEvent.ACTION_CANCEL -> {
                    view.parent?.requestDisallowInterceptTouchEvent(false)
                    holding = false
                    holdButton.isPressed = false
                    holdButton.text = "🎙  Hold to speak"
                    stopAndShutdown(commit = false)
                    true
                }

                else -> true
            }
        }
    }

    private fun hasAudioPermission(): Boolean =
        activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    private fun startListening(readyModel: Model) {
        releaseRecognizer()
        try {
            recognizer = Recognizer(readyModel, SAMPLE_RATE)
            speechService = SpeechService(recognizer, SAMPLE_RATE).also { service ->
                service.startListening(this)
            }
            status.text = "Listening… keep holding while you speak."
        } catch (error: Exception) {
            status.text = "Could not start Vosk: ${error.message ?: "unknown error"}"
            holdButton.text = "🎙  Hold to speak"
            holding = false
            releaseRecognizer()
        }
    }

    private fun stopAndShutdown(commit: Boolean) {
        val service = speechService
        speechService = null

        if (service == null) {
            if (commit) commitBestAvailableText()
            return
        }

        try { service.stop() } catch (_: Exception) {}
        try { service.shutdown() } catch (_: Exception) {}

        if (commit) {
            mainHandler.postDelayed({ commitBestAvailableText() }, FINAL_FALLBACK_DELAY_MS)
        } else {
            committed.set(true)
            releaseRecognizer()
            status.text = "Recording cancelled."
        }
    }

    override fun onPartialResult(hypothesis: String?) {
        val partial = extractJson(hypothesis, "partial")
        if (partial.isBlank()) return
        lastPartial = partial
        activity.runOnUiThread {
            if (holding) status.text = partial
        }
    }

    override fun onResult(hypothesis: String?) {
        val text = extractJson(hypothesis, "text")
        if (text.isNotBlank()) lastResult = text
    }

    override fun onFinalResult(hypothesis: String?) {
        val text = extractJson(hypothesis, "text")
        if (text.isNotBlank()) lastResult = text
        activity.runOnUiThread { commitBestAvailableText() }
    }

    override fun onError(exception: Exception?) {
        activity.runOnUiThread {
            if (!committed.get()) {
                if (!holding) commitBestAvailableText()
                else status.text = "Voice recognition error: ${exception?.message ?: "unknown error"}"
            }
        }
    }

    override fun onTimeout() {
        activity.runOnUiThread {
            if (!holding) commitBestAvailableText()
            else status.text = "No speech detected yet…"
        }
    }

    private fun commitBestAvailableText() {
        if (!committed.compareAndSet(false, true)) return

        val raw = when {
            lastResult.isNotBlank() -> lastResult
            lastPartial.isNotBlank() -> lastPartial
            else -> ""
        }

        if (raw.isBlank()) {
            status.text = "No speech recognized. Hold and try again."
            holdButton.text = "🎙  Hold to speak"
            releaseRecognizer()
            return
        }

        val normalized = normalizer.apply(raw).trim()
        input.setText(normalized)
        input.setSelection(normalized.length)
        status.text = "Voice answer: $normalized"
        holdButton.text = "🎙  Hold to speak"
        releaseRecognizer()
        onCommitted.run()
    }

    private fun extractJson(hypothesis: String?, key: String): String {
        if (hypothesis.isNullOrBlank()) return ""
        return try { JSONObject(hypothesis).optString(key).trim() } catch (_: Exception) { "" }
    }

    private fun loadModelAsync() {
        modelLoading = true
        holdButton.isEnabled = false
        holdButton.text = "Loading voice model…"
        status.text = "Preparing offline Vosk recognition…"

        scope.launch {
            try {
                val loaded = withContext(Dispatchers.IO) {
                    val modelDir = prepareModelDirectory()
                    Model(modelDir.absolutePath)
                }
                model = loaded
                modelLoading = false
                holdButton.isEnabled = true
                holdButton.text = "🎙  Hold to speak"
                status.text = "Offline voice recognition ready."
            } catch (error: Exception) {
                modelLoading = false
                holdButton.isEnabled = false
                holdButton.text = "Voice model unavailable"
                status.text = "Could not load Vosk model: ${error.message ?: "unknown error"}"
            }
        }
    }

    private fun prepareModelDirectory(): File {
        val target = File(activity.filesDir, MODEL_DIR_NAME)
        val ready = File(target, READY_MARKER)
        if (target.isDirectory && ready.isFile) return target

        if (target.exists()) target.deleteRecursively()
        target.mkdirs()
        copyAssetTree(MODEL_ASSET_PATH, target)
        ready.createNewFile()
        return target
    }

    private fun copyAssetTree(assetPath: String, target: File) {
        val children = activity.assets.list(assetPath) ?: error("Missing asset: $assetPath")
        if (children.isEmpty()) {
            target.parentFile?.mkdirs()
            activity.assets.open(assetPath).use { inputStream ->
                FileOutputStream(target).use { outputStream -> inputStream.copyTo(outputStream) }
            }
            return
        }

        target.mkdirs()
        children.forEach { child ->
            copyAssetTree("$assetPath/$child", File(target, child))
        }
    }

    fun onPermissionResult(requestCode: Int, grantResults: IntArray): Boolean {
        if (requestCode != REQUEST_RECORD_AUDIO) return false
        status.text = if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            "Microphone permission granted. Hold the button to speak."
        } else {
            "Microphone permission is required for voice answers."
        }
        return true
    }

    fun onPause() {
        holding = false
        stopAndShutdown(commit = false)
    }

    fun onStop() {
        holding = false
        stopAndShutdown(commit = false)
    }

    fun destroy() {
        holding = false
        stopAndShutdown(commit = false)
        releaseRecognizer()
        model?.close()
        model = null
        scope.cancel()
    }

    private fun releaseRecognizer() {
        try { recognizer?.close() } catch (_: Exception) {}
        recognizer = null
    }

    companion object {
        private const val SAMPLE_RATE = 16_000.0f
        private const val REQUEST_RECORD_AUDIO = 1001
        private const val MODEL_ASSET_PATH = "vosk-model"
        private const val MODEL_DIR_NAME = "vosk-model"
        private const val READY_MARKER = ".ready"
        private const val FINAL_FALLBACK_DELAY_MS = 250L
    }
}
