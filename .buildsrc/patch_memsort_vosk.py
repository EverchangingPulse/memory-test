from pathlib import Path

path = Path('app/src/main/java/com/example/dualqueue/MainActivity.java')
source = path.read_text()

source = source.replace(
    '    private SpeechRecognizer speechRecognizer;\n',
    '    private SpeechRecognizer speechRecognizer;\n    private VoskHoldRecorder voskHoldRecorder;\n'
)

source = source.replace(
    '        setupSpeechRecognizer();\n        renderProgress();',
    '        voskHoldRecorder = new VoskHoldRecorder(this, holdToSpeak, response, status, this::normalizeSpokenAnswer, () -> {\n'
    '            if (setRunning && !correct.isEmpty()) submitAnswer(false);\n'
    '        });\n'
    '        renderProgress();'
)

source = source.replace(
    'TextView title = text("Dual-Queue Running Insertion", 28, true);',
    'TextView title = text("MemSort", 28, true);'
)

source = source.replace(
    '        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n',
    '        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n'
    '        if (voskHoldRecorder != null && voskHoldRecorder.onPermissionResult(requestCode, grantResults)) return;\n'
)

source = source.replace(
    '    @Override\n    protected void onDestroy() {\n        super.onDestroy();',
    '    @Override\n'
    '    protected void onPause() {\n'
    '        if (voskHoldRecorder != null) voskHoldRecorder.onPause();\n'
    '        super.onPause();\n'
    '    }\n\n'
    '    @Override\n'
    '    protected void onStop() {\n'
    '        if (voskHoldRecorder != null) voskHoldRecorder.onStop();\n'
    '        super.onStop();\n'
    '    }\n\n'
    '    @Override\n'
    '    protected void onDestroy() {\n'
    '        if (voskHoldRecorder != null) {\n'
    '            voskHoldRecorder.destroy();\n'
    '            voskHoldRecorder = null;\n'
    '        }\n'
    '        super.onDestroy();'
)

path.write_text(source)
