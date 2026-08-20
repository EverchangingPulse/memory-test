from pathlib import Path

java_path = Path('app/src/main/java/com/example/dualqueue/MainActivity.java')
source = java_path.read_text()


def replace_once(text, old, new, label):
    if old not in text:
        raise RuntimeError(f'Patch target not found: {label}')
    return text.replace(old, new, 1)


source = replace_once(
    source,
    '    private SpeechRecognizer speechRecognizer;\n',
    '    private SpeechRecognizer speechRecognizer;\n    private VoskHoldRecorder voskHoldRecorder;\n',
    'Vosk field'
)

source = replace_once(
    source,
    '    private Spinner focusNumbers;\n',
    '    private Spinner focusNumbers;\n    private Spinner numberOrder;\n    private Spinner letterOrder;\n',
    'order fields'
)

source = replace_once(
    source,
    '        setupSpeechRecognizer();\n        renderProgress();',
    '        voskHoldRecorder = new VoskHoldRecorder(\n'
    '                this, holdToSpeak, response, status,\n'
    '                () -> selectedLanguage(answerLanguage),\n'
    '                () -> selected(itemType),\n'
    '                () -> numberRangeMin(),\n'
    '                () -> numberRangeMax(),\n'
    '                this::normalizeSpokenAnswer,\n'
    '                () -> { if (setRunning && !correct.isEmpty()) submitAnswer(false); });\n'
    '        answerLanguage.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {\n'
    '            @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {\n'
    '                if (voskHoldRecorder != null) voskHoldRecorder.onLanguageChanged(selectedLanguage(answerLanguage));\n'
    '            }\n'
    '            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}\n'
    '        });\n'
    '        renderProgress();',
    'Vosk initialization'
)

source = replace_once(
    source,
    'TextView title = text("Dual-Queue Running Insertion", 28, true);',
    'TextView title = text("MemSort", 28, true);',
    'title'
)

source = replace_once(
    source,
    '        numberRange = spinner(new String[]{"1–9", "1–20", "1–50", "1–99"});\n        numberRange.setSelection(1);',
    '        numberRange = spinner(new String[]{"0–9", "1–9", "1–20", "1–50", "1–99"});',
    'number range defaults'
)

source = replace_once(
    source,
    '        letterCase = spinner(new String[]{"UPPER", "lower", "MiXeD"});\n        addField(config, "Letter case", letterCase);',
    '        letterCase = spinner(new String[]{"UPPER", "lower", "MiXeD"});\n'
    '        addField(config, "Letter case", letterCase);\n\n'
    '        numberOrder = spinner(new String[]{"Ascending", "Descending"});\n'
    '        letterOrder = spinner(new String[]{"Alphabetical", "Reverse alphabetical"});\n'
    '        addField(config, "Number answer order", numberOrder);\n'
    '        addField(config, "Letter answer order", letterOrder);',
    'order controls'
)

source = replace_once(
    source,
    '        focusLength = spinner(new String[]{"Balanced", "More short exercises", "More long exercises", "Mostly maximum length"});',
    '        focusLength = spinner(new String[]{"Progressive (1 → maximum)", "Random", "Fixed at maximum", "Random: more short", "Random: more long", "Random: mostly maximum"});',
    'length mode options'
)
source = replace_once(
    source,
    '        addField(config, "Character-count focus", focusLength);',
    '        addField(config, "Exercise length", focusLength);',
    'length label'
)

source = replace_once(
    source,
    '        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n',
    '        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n'
    '        if (voskHoldRecorder != null && voskHoldRecorder.onPermissionResult(requestCode, grantResults)) return;\n',
    'permission routing'
)

source = replace_once(
    source,
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
    '        super.onDestroy();',
    'Vosk lifecycle'
)

source = replace_once(
    source,
    '        if (user.equals(expected)) return new Validation("CORRECT", "No error", true);',
    '        if (isCorrectAnswer(user, expected)) return new Validation("CORRECT", "No error", true);',
    'swapped validation'
)

validate_marker = '    private Validation validate(List<String> user, List<String> expected) {'
helper = '''    private boolean isCorrectAnswer(List<String> user, List<String> expected) {
        if (user.equals(expected)) return true;
        List<String> swapped = new ArrayList<>();
        swapped.addAll(filterLetters(expected));
        swapped.addAll(filterNumbers(expected));
        return user.equals(swapped);
    }

'''
if validate_marker not in source:
    raise RuntimeError('Patch target not found: validation helper marker')
source = source.replace(validate_marker, helper + validate_marker, 1)

source = replace_once(
    source,
    '        for (int i = 1; i <= nr; i++) numberPool.add(i);',
    '        for (int i = numberRangeMin(); i <= nr; i++) numberPool.add(i);',
    'number pool minimum'
)

weighted_start = source.index('    private int weightedLength(int max) {')
weighted_end = source.index('    private List<String> determineCorrect', weighted_start)
source = source[:weighted_start] + '''    private int weightedLength(int max) {
        String lengthMode = selected(focusLength);
        if (lengthMode.startsWith("Progressive")) return Math.min(max, setIndex + 1);
        if (lengthMode.startsWith("Fixed")) return max;
        if (lengthMode.equals("Random")) return random.nextInt(max) + 1;
        if (lengthMode.equals("Random: mostly maximum") && random.nextDouble() < .75) return max;

        int total = max * (max + 1) / 2;
        int roll = random.nextInt(total) + 1;
        int acc = 0;
        for (int len = 1; len <= max; len++) {
            int weight = lengthMode.equals("Random: more short") ? max - len + 1 : len;
            acc += weight;
            if (roll <= acc) return len;
        }
        return max;
    }

''' + source[weighted_end:]

determine_start = source.index('    private List<String> determineCorrect(List<Object> seq) {')
determine_end = source.index('    private List<String> parseResponse', determine_start)
source = source[:determine_start] + '''    private List<String> determineCorrect(List<Object> seq) {
        List<Integer> nums = new ArrayList<>();
        List<String> lets = new ArrayList<>();
        for (Object x : seq) {
            if (x instanceof Integer) nums.add((Integer) x);
            else lets.add(String.valueOf(x).toUpperCase(Locale.ROOT));
        }
        Collections.sort(nums);
        Collections.sort(lets);
        if (selected(numberOrder).equals("Descending")) Collections.reverse(nums);
        if (selected(letterOrder).equals("Reverse alphabetical")) Collections.reverse(lets);

        List<String> out = new ArrayList<>();
        for (Integer n : nums) out.add(String.valueOf(n));
        out.addAll(lets);
        return out;
    }

''' + source[determine_end:]

source = replace_once(
    source,
    '    private int numberRangeMax() { return new int[]{9, 20, 50, 99}[numberRange.getSelectedItemPosition()]; }',
    '    private int numberRangeMin() { return numberRange.getSelectedItemPosition() == 0 ? 0 : 1; }\n'
    '    private int numberRangeMax() { return new int[]{9, 9, 20, 50, 99}[numberRange.getSelectedItemPosition()]; }',
    'number range helpers'
)

java_path.write_text(source)

vosk_path = Path('app/src/main/java/com/example/dualqueue/VoskHoldRecorder.kt')
vosk = vosk_path.read_text()
vosk = replace_once(
    vosk,
    '    private val numberRangeSupplier: Supplier<Int>,\n',
    '    private val numberRangeMinSupplier: Supplier<Int>,\n    private val numberRangeSupplier: Supplier<Int>,\n',
    'Vosk range suppliers'
)
vosk = replace_once(
    vosk,
    '                        itemTypeSupplier.get(),\n                        numberRangeSupplier.get()\n',
    '                        itemTypeSupplier.get(),\n                        numberRangeMinSupplier.get(),\n                        numberRangeSupplier.get()\n',
    'Vosk vocabulary arguments'
)
vosk = replace_once(
    vosk,
    '        itemType: String,\n        maxNumber: Int\n',
    '        itemType: String,\n        minNumber: Int,\n        maxNumber: Int\n',
    'Vosk vocabulary signature'
)
vosk = replace_once(
    vosk,
    '            for (number in 1..maxNumber.coerceIn(1, 99)) allowedTokens += number.toString()',
    '            val low = minNumber.coerceIn(0, 99)\n            val high = maxNumber.coerceIn(low, 99)\n            for (number in low..high) allowedTokens += number.toString()',
    'Vosk allowed number range'
)
vosk_path.write_text(vosk)
