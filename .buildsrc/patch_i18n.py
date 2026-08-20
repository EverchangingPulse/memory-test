from pathlib import Path
import re

java = Path('app/src/main/java/com/example/dualqueue/MainActivity.java')
s = java.read_text()

s = s.replace(
    '        setContentView(buildUi());\n',
    '        setContentView(buildUi());\n'
    '        int phoneLanguageIndex = phoneLanguageIndex();\n'
    '        presentationLanguage.setSelection(phoneLanguageIndex);\n'
    '        answerLanguage.setSelection(phoneLanguageIndex);\n',
    1
)

s = s.replace('        v.setText(s);', '        v.setText(AppStrings.t(s));', 1)
s = s.replace('        b.setText(s);', '        b.setText(AppStrings.t(s));', 1)
s = s.replace('        response.setHint("Enter sorted answer");', '        response.setHint(AppStrings.t("Enter sorted answer"));', 1)
s = s.replace(
    '        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);',
    '        String[] localizedValues = new String[values.length];\n'
    '        for (int i = 0; i < values.length; i++) localizedValues[i] = AppStrings.t(values[i]);\n'
    '        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localizedValues);',
    1
)
s = s.replace(
    '    private String selected(Spinner spinner) { return String.valueOf(spinner.getSelectedItem()); }',
    '    private String selected(Spinner spinner) { return AppStrings.canonical(String.valueOf(spinner.getSelectedItem())); }\n'
    '    private int phoneLanguageIndex() {\n'
    '        String language = Locale.getDefault().getLanguage();\n'
    '        for (int i = 0; i < LANG_TAGS.length; i++) if (LANG_TAGS[i].startsWith(language)) return i;\n'
    '        return 0;\n'
    '    }',
    1
)

for name in ['status', 'counter', 'setStats', 'classification', 'longTermProgress', 'nextItem', 'holdToSpeak']:
    pattern = re.compile(rf'({name}\.setText)\(([^;\n]+)\);')
    s = pattern.sub(r'\1(AppStrings.t(\2));', s)

java.write_text(s)

kotlin = Path('app/src/main/java/com/example/dualqueue/VoskHoldRecorder.kt')
k = kotlin.read_text()

permission_block = '''        status.text = if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            "Microphone permission granted. Hold the button to speak."
        } else {
            "Microphone permission is required for voice answers."
        }'''
localized_permission_block = '''        status.text = AppStrings.t(if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            "Microphone permission granted. Hold the button to speak."
        } else {
            "Microphone permission is required for voice answers."
        })'''
k = k.replace(permission_block, localized_permission_block, 1)

safe_status = re.compile(r'^(\s*status\.text\s*=\s*)(?!if\s*\{?)(?!if\s*\()(.+)$', re.MULTILINE)
safe_button = re.compile(r'^(\s*holdButton\.text\s*=\s*)(.+)$', re.MULTILINE)

def wrap(match):
    rhs = match.group(2).rstrip()
    if rhs.startswith('AppStrings.t('):
        return match.group(0)
    if rhs.endswith('{'):
        return match.group(0)
    return match.group(1) + 'AppStrings.t(' + rhs + ')'

k = safe_status.sub(wrap, k)
k = safe_button.sub(wrap, k)
kotlin.write_text(k)
