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
k = re.sub(r'(status\.text\s*=\s*)([^\n]+)', lambda m: m.group(1) + 'AppStrings.t(' + m.group(2).rstrip() + ')', k)
k = re.sub(r'(holdButton\.text\s*=\s*)([^\n]+)', lambda m: m.group(1) + 'AppStrings.t(' + m.group(2).rstrip() + ')', k)
kotlin.write_text(k)
