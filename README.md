# Dual-Queue Running Insertion — Android

Native Android application targeting Android 12 (API 31) and above.

## Native hold-to-speak behavior

The microphone button uses Android `MotionEvent` directly:

- `ACTION_DOWN`: finger touches the button; voice recognition starts.
- `ACTION_UP`: finger is lifted; recognition stops and the answer is submitted.
- `ACTION_CANCEL`: Android cancels the gesture; recognition is cancelled without submitting.

The app prefers Android's on-device `SpeechRecognizer` when one is installed and available, and falls back to the device recognition service otherwise.

## Main features

- Audio, visual, both, and self-paced presentation.
- English, Italian, French, Spanish, German, and Japanese presentation languages.
- Independent voice-answer language.
- Native Android TextToSpeech.
- Native Android SpeechRecognizer with press-and-hold microphone interaction.
- Italian Y pronounced as “Ypsilon”.
- Configurable maximum character count, numbers/letters/mixed composition, number range, mixed ratio, letter case, answer separator, interval, and set size.
- Exercise targeting by character count, number count, and letter count.
- Automatic validation with exact error classification.
- Per-set average time, total time, accuracy, structural classification, and final per-exercise summary.
- Long-term structural accuracy stored with SharedPreferences.

## Build

The included GitHub Actions workflow builds a debug APK on every push to `main` and uploads it as the `dual-queue-debug-apk` artifact.

Local requirements:

- JDK 17
- Android SDK Platform 35
- Gradle 8.9

From the project directory:

```bash
gradle assembleDebug
```

The debug APK is produced at:

`app/build/outputs/apk/debug/app-debug.apk`
