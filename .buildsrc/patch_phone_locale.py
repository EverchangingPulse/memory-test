from pathlib import Path

p=Path('app/src/main/java/com/example/dualqueue/MainActivity.java')
s=p.read_text()

s=s.replace('        setContentView(buildUi());\n', '        setContentView(buildUi());\n        int phoneLanguageIndex = phoneLanguageIndex();\n        presentationLanguage.setSelection(phoneLanguageIndex);\n        answerLanguage.setSelection(phoneLanguageIndex);\n')

s=s.replace('    private TextView text(String s, int sp, boolean bold) {\n', '''    private String ui(String value) {
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("it")) {
            switch (value) {
                case "Native Android trainer with press-and-hold voice answers": return "Allenatore Android nativo con risposte vocali tenendo premuto";
                case "Exercise configuration": return "Configurazione esercizio";
                case "Presentation language": return "Lingua di presentazione";
                case "Voice-answer language": return "Lingua risposta vocale";
                case "Items": return "Elementi";
                case "Maximum characters": return "Caratteri massimi";
                case "Number range": return "Intervallo numeri";
                case "Numbers in mixed exercises": return "Numeri negli esercizi misti";
                case "Letter case": return "Maiuscole/minuscole";
                case "Answer separator": return "Separatore risposta";
                case "Exercises in this set": return "Esercizi nel set";
                case "Presentation interval": return "Intervallo di presentazione";
                case "Presentation mode": return "Modalità di presentazione";
                case "Length progression": return "Progressione lunghezza";
                case "Character-count focus": return "Distribuzione lunghezza";
                case "Letter-count focus": return "Distribuzione lettere";
                case "Number-count focus": return "Distribuzione numeri";
                case "Number order": return "Ordine numeri";
                case "Letter order": return "Ordine lettere";
                case "Start exercise set": return "Avvia set di esercizi";
                case "Stop set": return "Ferma set";
                case "Current exercise": return "Esercizio corrente";
                case "Next item": return "Elemento successivo";
                case "Submit and continue": return "Invia e continua";
                case "Reveal": return "Mostra risposta";
                case "Set statistics": return "Statistiche set";
                case "Set summary": return "Riepilogo set";
                case "Long-term progress": return "Progressi nel tempo";
                case "Numbers + letters": return "Numeri + lettere";
                case "Numbers only": return "Solo numeri";
                case "Letters only": return "Solo lettere";
                case "Progressive 1 → maximum": return "Progressivo 1 → massimo";
                case "Random": return "Casuale";
                case "Fixed at maximum": return "Fisso al massimo";
                case "Ascending": return "Crescente";
                case "Descending": return "Decrescente";
                case "Alphabetical": return "Alfabetico";
                case "Reverse alphabetical": return "Alfabetico inverso";
                case "Audio": return "Audio";
                case "Visual": return "Visivo";
                case "Both": return "Entrambi";
                case "Self-paced": return "Autogestito";
                case "Spaces": return "Spazi";
                case "Nothing": return "Nessuno";
                default: return value;
            }
        }
        if (lang.equals("fr")) {
            switch (value) {
                case "Exercise configuration": return "Configuration de l’exercice"; case "Presentation language": return "Langue de présentation"; case "Voice-answer language": return "Langue de réponse vocale"; case "Items": return "Éléments"; case "Maximum characters": return "Caractères maximum"; case "Number range": return "Plage de nombres"; case "Start exercise set": return "Démarrer la série"; case "Stop set": return "Arrêter la série"; case "Current exercise": return "Exercice actuel"; case "Submit and continue": return "Valider et continuer"; case "Reveal": return "Afficher"; case "Set statistics": return "Statistiques"; case "Set summary": return "Résumé"; case "Long-term progress": return "Progression"; case "Numbers + letters": return "Nombres + lettres"; case "Numbers only": return "Nombres uniquement"; case "Letters only": return "Lettres uniquement"; case "Ascending": return "Croissant"; case "Descending": return "Décroissant"; case "Alphabetical": return "Alphabétique"; case "Reverse alphabetical": return "Alphabétique inverse"; case "Random": return "Aléatoire"; case "Fixed at maximum": return "Fixe au maximum"; default: return value;
            }
        }
        if (lang.equals("es")) {
            switch (value) {
                case "Exercise configuration": return "Configuración del ejercicio"; case "Presentation language": return "Idioma de presentación"; case "Voice-answer language": return "Idioma de respuesta por voz"; case "Items": return "Elementos"; case "Maximum characters": return "Caracteres máximos"; case "Number range": return "Rango numérico"; case "Start exercise set": return "Iniciar serie"; case "Stop set": return "Detener serie"; case "Current exercise": return "Ejercicio actual"; case "Submit and continue": return "Enviar y continuar"; case "Reveal": return "Mostrar"; case "Set statistics": return "Estadísticas"; case "Set summary": return "Resumen"; case "Long-term progress": return "Progreso"; case "Numbers + letters": return "Números + letras"; case "Numbers only": return "Solo números"; case "Letters only": return "Solo letras"; case "Ascending": return "Ascendente"; case "Descending": return "Descendente"; case "Alphabetical": return "Alfabético"; case "Reverse alphabetical": return "Alfabético inverso"; case "Random": return "Aleatorio"; case "Fixed at maximum": return "Fijo al máximo"; default: return value;
            }
        }
        if (lang.equals("de")) {
            switch (value) {
                case "Exercise configuration": return "Übungskonfiguration"; case "Presentation language": return "Ausgabesprache"; case "Voice-answer language": return "Spracheingabe"; case "Items": return "Elemente"; case "Maximum characters": return "Maximale Zeichen"; case "Number range": return "Zahlenbereich"; case "Start exercise set": return "Übungsserie starten"; case "Stop set": return "Serie stoppen"; case "Current exercise": return "Aktuelle Übung"; case "Submit and continue": return "Senden und weiter"; case "Reveal": return "Anzeigen"; case "Set statistics": return "Statistik"; case "Set summary": return "Zusammenfassung"; case "Long-term progress": return "Langzeitfortschritt"; case "Numbers + letters": return "Zahlen + Buchstaben"; case "Numbers only": return "Nur Zahlen"; case "Letters only": return "Nur Buchstaben"; case "Ascending": return "Aufsteigend"; case "Descending": return "Absteigend"; case "Alphabetical": return "Alphabetisch"; case "Reverse alphabetical": return "Umgekehrt alphabetisch"; case "Random": return "Zufällig"; case "Fixed at maximum": return "Fest auf Maximum"; default: return value;
            }
        }
        if (lang.equals("ja")) {
            switch (value) {
                case "Exercise configuration": return "トレーニング設定"; case "Presentation language": return "出力言語"; case "Voice-answer language": return "音声入力言語"; case "Items": return "項目"; case "Maximum characters": return "最大文字数"; case "Number range": return "数値範囲"; case "Start exercise set": return "トレーニング開始"; case "Stop set": return "停止"; case "Current exercise": return "現在の問題"; case "Submit and continue": return "送信して続行"; case "Reveal": return "答えを表示"; case "Set statistics": return "統計"; case "Set summary": return "概要"; case "Long-term progress": return "長期進捗"; case "Numbers + letters": return "数字 + 文字"; case "Numbers only": return "数字のみ"; case "Letters only": return "文字のみ"; case "Ascending": return "昇順"; case "Descending": return "降順"; case "Alphabetical": return "アルファベット順"; case "Reverse alphabetical": return "逆アルファベット順"; case "Random": return "ランダム"; case "Fixed at maximum": return "最大で固定"; default: return value;
            }
        }
        return value;
    }

    private int phoneLanguageIndex() {
        String lang = Locale.getDefault().getLanguage();
        for (int i = 0; i < LANG_TAGS.length; i++) if (LANG_TAGS[i].startsWith(lang)) return i;
        return 0;
    }

    private TextView text(String s, int sp, boolean bold) {
        s = ui(s);
''')

s=s.replace('        b.setText(s);\n', '        b.setText(ui(s));\n')
s=s.replace('        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);\n', '        String[] localizedValues = new String[values.length];\n        for (int i = 0; i < values.length; i++) localizedValues[i] = ui(values[i]);\n        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localizedValues);\n')
s=s.replace('        response.setHint("Enter sorted answer");', '        response.setHint(ui("Enter sorted answer"));')

p.write_text(s)
