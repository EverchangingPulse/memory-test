package com.example.dualqueue;

import java.util.Locale;

public final class AppStrings {
    private AppStrings() {}

    private static final String[] CANONICAL_OPTIONS = {
            "Numbers + letters", "Numbers only", "Letters only",
            "UPPER", "lower", "MiXeD",
            "Nothing", "Spaces", "Hyphens (-)", "Commas (,)",
            "Audio", "Visual", "Both", "Self-paced",
            "Progressive (1 → maximum)", "Random", "Fixed at maximum",
            "Random: more short", "Random: more long", "Random: mostly maximum",
            "Balanced", "More letters", "Fewer letters", "More numbers", "Fewer numbers",
            "Ascending", "Descending", "Alphabetical", "Reverse alphabetical"
    };

    public static String canonical(String displayed) {
        for (String canonical : CANONICAL_OPTIONS) {
            if (t(canonical).equals(displayed)) return canonical;
        }
        return displayed;
    }

    public static String t(String value) {
        if (value == null) return "";
        String language = Locale.getDefault().getLanguage();
        String translated = translateExact(language, value);
        if (!translated.equals(value)) return translated;
        return translateDynamic(language, value);
    }

    private static String translateExact(String language, String value) {
        switch (language) {
            case "it": return it(value);
            case "fr": return fr(value);
            case "es": return es(value);
            case "de": return de(value);
            case "ja": return ja(value);
            default: return value;
        }
    }

    private static String it(String s) {
        switch (s) {
            case "Native Android trainer with press-and-hold voice answers": return "Allenatore Android nativo con risposte vocali tenendo premuto";
            case "Exercise configuration": return "Configurazione esercizio";
            case "Presentation language": return "Lingua di presentazione";
            case "Voice-answer language": return "Lingua della risposta vocale";
            case "Items": return "Elementi";
            case "Maximum characters": return "Caratteri massimi";
            case "Number range": return "Intervallo numeri";
            case "Numbers in mixed exercises": return "Numeri negli esercizi misti";
            case "Letter case": return "Maiuscole/minuscole";
            case "Number answer order": return "Ordine dei numeri";
            case "Letter answer order": return "Ordine delle lettere";
            case "Answer separator": return "Separatore risposta";
            case "Exercises in this set": return "Esercizi nel set";
            case "Presentation interval": return "Intervallo di presentazione";
            case "Presentation mode": return "Modalità di presentazione";
            case "Exercise length": return "Lunghezza esercizio";
            case "Letter-count focus": return "Distribuzione lettere";
            case "Number-count focus": return "Distribuzione numeri";
            case "Start exercise set": return "Avvia set di esercizi";
            case "Stop set": return "Ferma set";
            case "Current exercise": return "Esercizio corrente";
            case "Ready": return "Pronto";
            case "Configure the set and start.": return "Configura il set e avvia.";
            case "Next item": return "Elemento successivo";
            case "Finish sequence": return "Termina sequenza";
            case "Enter sorted answer": return "Inserisci la risposta ordinata";
            case "Hold the microphone button like in a messaging app: keep finger down while speaking, lift to send.": return "Tieni premuto il microfono come in un'app di messaggistica: parla mentre tieni premuto e rilascia per inviare.";
            case "Submit and continue": return "Invia e continua";
            case "Reveal": return "Mostra risposta";
            case "Set statistics": return "Statistiche del set";
            case "Set summary": return "Riepilogo del set";
            case "Long-term progress": return "Progressi nel tempo";
            case "No completed exercises yet.": return "Nessun esercizio completato.";
            case "No saved exercises yet.": return "Nessun esercizio salvato.";
            case "Sequence complete. Answer from memory.": return "Sequenza completata. Rispondi a memoria.";
            case "Starting…": return "Avvio…";
            case "Set complete.": return "Set completato.";
            case "Set stopped.": return "Set interrotto.";
            case "Numbers + letters": return "Numeri + lettere";
            case "Numbers only": return "Solo numeri";
            case "Letters only": return "Solo lettere";
            case "Nothing": return "Nessuno";
            case "Spaces": return "Spazi";
            case "Hyphens (-)": return "Trattini (-)";
            case "Commas (, )": return "Virgole (,)";
            case "Audio": return "Audio";
            case "Visual": return "Visivo";
            case "Both": return "Entrambi";
            case "Self-paced": return "Autogestito";
            case "Progressive (1 → maximum)": return "Progressivo (1 → massimo)";
            case "Random": return "Casuale";
            case "Fixed at maximum": return "Fisso al massimo";
            case "Random: more short": return "Casuale: più brevi";
            case "Random: more long": return "Casuale: più lunghi";
            case "Random: mostly maximum": return "Casuale: soprattutto massimo";
            case "Balanced": return "Bilanciato";
            case "More letters": return "Più lettere";
            case "Fewer letters": return "Meno lettere";
            case "More numbers": return "Più numeri";
            case "Fewer numbers": return "Meno numeri";
            case "Ascending": return "Crescente";
            case "Descending": return "Decrescente";
            case "Alphabetical": return "Alfabetico";
            case "Reverse alphabetical": return "Alfabetico inverso";
            case "🎙  Hold to speak": return "🎙  Tieni premuto per parlare";
            case "🔴 Recording — lift to send": return "🔴 Registrazione — rilascia per inviare";
            case "⏳ Transcribing…": return "⏳ Trascrizione…";
            default: return s;
        }
    }

    private static String fr(String s) {
        switch (s) {
            case "Exercise configuration": return "Configuration de l’exercice";
            case "Presentation language": return "Langue de présentation";
            case "Voice-answer language": return "Langue de réponse vocale";
            case "Items": return "Éléments";
            case "Maximum characters": return "Caractères maximum";
            case "Number range": return "Plage de nombres";
            case "Numbers in mixed exercises": return "Nombres dans les exercices mixtes";
            case "Letter case": return "Casse des lettres";
            case "Number answer order": return "Ordre des nombres";
            case "Letter answer order": return "Ordre des lettres";
            case "Answer separator": return "Séparateur de réponse";
            case "Exercises in this set": return "Exercices dans la série";
            case "Presentation interval": return "Intervalle de présentation";
            case "Presentation mode": return "Mode de présentation";
            case "Exercise length": return "Longueur de l’exercice";
            case "Start exercise set": return "Démarrer la série";
            case "Stop set": return "Arrêter la série";
            case "Current exercise": return "Exercice actuel";
            case "Ready": return "Prêt";
            case "Configure the set and start.": return "Configurez la série puis démarrez.";
            case "Next item": return "Élément suivant";
            case "Finish sequence": return "Terminer la séquence";
            case "Enter sorted answer": return "Saisissez la réponse triée";
            case "Submit and continue": return "Valider et continuer";
            case "Reveal": return "Afficher la réponse";
            case "Set statistics": return "Statistiques de la série";
            case "Set summary": return "Résumé de la série";
            case "Long-term progress": return "Progression à long terme";
            case "Sequence complete. Answer from memory.": return "Séquence terminée. Répondez de mémoire.";
            case "Starting…": return "Démarrage…";
            case "Set complete.": return "Série terminée.";
            case "Set stopped.": return "Série arrêtée.";
            case "Numbers + letters": return "Nombres + lettres";
            case "Numbers only": return "Nombres uniquement";
            case "Letters only": return "Lettres uniquement";
            case "Nothing": return "Aucun";
            case "Spaces": return "Espaces";
            case "Hyphens (-)": return "Tirets (-)";
            case "Commas (, )": return "Virgules (,)";
            case "Visual": return "Visuel";
            case "Both": return "Les deux";
            case "Self-paced": return "À votre rythme";
            case "Progressive (1 → maximum)": return "Progressif (1 → maximum)";
            case "Random": return "Aléatoire";
            case "Fixed at maximum": return "Fixe au maximum";
            case "Random: more short": return "Aléatoire : plus court";
            case "Random: more long": return "Aléatoire : plus long";
            case "Random: mostly maximum": return "Aléatoire : surtout maximum";
            case "Balanced": return "Équilibré";
            case "More letters": return "Plus de lettres";
            case "Fewer letters": return "Moins de lettres";
            case "More numbers": return "Plus de nombres";
            case "Fewer numbers": return "Moins de nombres";
            case "Ascending": return "Croissant";
            case "Descending": return "Décroissant";
            case "Alphabetical": return "Alphabétique";
            case "Reverse alphabetical": return "Alphabétique inverse";
            case "🎙  Hold to speak": return "🎙  Maintenir pour parler";
            case "🔴 Recording — lift to send": return "🔴 Enregistrement — relâchez pour envoyer";
            case "⏳ Transcribing…": return "⏳ Transcription…";
            default: return s;
        }
    }

    private static String es(String s) {
        switch (s) {
            case "Exercise configuration": return "Configuración del ejercicio";
            case "Presentation language": return "Idioma de presentación";
            case "Voice-answer language": return "Idioma de respuesta por voz";
            case "Items": return "Elementos";
            case "Maximum characters": return "Caracteres máximos";
            case "Number range": return "Rango numérico";
            case "Numbers in mixed exercises": return "Números en ejercicios mixtos";
            case "Letter case": return "Mayúsculas/minúsculas";
            case "Number answer order": return "Orden de números";
            case "Letter answer order": return "Orden de letras";
            case "Answer separator": return "Separador de respuesta";
            case "Exercises in this set": return "Ejercicios en la serie";
            case "Presentation interval": return "Intervalo de presentación";
            case "Presentation mode": return "Modo de presentación";
            case "Exercise length": return "Longitud del ejercicio";
            case "Start exercise set": return "Iniciar serie";
            case "Stop set": return "Detener serie";
            case "Current exercise": return "Ejercicio actual";
            case "Ready": return "Listo";
            case "Configure the set and start.": return "Configura la serie y empieza.";
            case "Next item": return "Siguiente elemento";
            case "Finish sequence": return "Finalizar secuencia";
            case "Enter sorted answer": return "Introduce la respuesta ordenada";
            case "Submit and continue": return "Enviar y continuar";
            case "Reveal": return "Mostrar respuesta";
            case "Set statistics": return "Estadísticas de la serie";
            case "Set summary": return "Resumen de la serie";
            case "Long-term progress": return "Progreso a largo plazo";
            case "Sequence complete. Answer from memory.": return "Secuencia completa. Responde de memoria.";
            case "Starting…": return "Iniciando…";
            case "Set complete.": return "Serie completada.";
            case "Set stopped.": return "Serie detenida.";
            case "Numbers + letters": return "Números + letras";
            case "Numbers only": return "Solo números";
            case "Letters only": return "Solo letras";
            case "Nothing": return "Ninguno";
            case "Spaces": return "Espacios";
            case "Hyphens (-)": return "Guiones (-)";
            case "Commas (, )": return "Comas (,)";
            case "Visual": return "Visual";
            case "Both": return "Ambos";
            case "Self-paced": return "A tu ritmo";
            case "Progressive (1 → maximum)": return "Progresivo (1 → máximo)";
            case "Random": return "Aleatorio";
            case "Fixed at maximum": return "Fijo al máximo";
            case "Random: more short": return "Aleatorio: más cortos";
            case "Random: more long": return "Aleatorio: más largos";
            case "Random: mostly maximum": return "Aleatorio: sobre todo máximo";
            case "Balanced": return "Equilibrado";
            case "More letters": return "Más letras";
            case "Fewer letters": return "Menos letras";
            case "More numbers": return "Más números";
            case "Fewer numbers": return "Menos números";
            case "Ascending": return "Ascendente";
            case "Descending": return "Descendente";
            case "Alphabetical": return "Alfabético";
            case "Reverse alphabetical": return "Alfabético inverso";
            case "🎙  Hold to speak": return "🎙  Mantén pulsado para hablar";
            case "🔴 Recording — lift to send": return "🔴 Grabando — suelta para enviar";
            case "⏳ Transcribing…": return "⏳ Transcribiendo…";
            default: return s;
        }
    }

    private static String de(String s) {
        switch (s) {
            case "Exercise configuration": return "Übungskonfiguration";
            case "Presentation language": return "Ausgabesprache";
            case "Voice-answer language": return "Spracheingabe";
            case "Items": return "Elemente";
            case "Maximum characters": return "Maximale Zeichen";
            case "Number range": return "Zahlenbereich";
            case "Numbers in mixed exercises": return "Zahlen in gemischten Übungen";
            case "Letter case": return "Groß-/Kleinschreibung";
            case "Number answer order": return "Zahlenreihenfolge";
            case "Letter answer order": return "Buchstabenreihenfolge";
            case "Answer separator": return "Antwort-Trennzeichen";
            case "Exercises in this set": return "Übungen im Satz";
            case "Presentation interval": return "Anzeigeintervall";
            case "Presentation mode": return "Darstellungsmodus";
            case "Exercise length": return "Übungslänge";
            case "Start exercise set": return "Übungsserie starten";
            case "Stop set": return "Serie stoppen";
            case "Current exercise": return "Aktuelle Übung";
            case "Ready": return "Bereit";
            case "Configure the set and start.": return "Serie konfigurieren und starten.";
            case "Next item": return "Nächstes Element";
            case "Finish sequence": return "Sequenz beenden";
            case "Enter sorted answer": return "Sortierte Antwort eingeben";
            case "Submit and continue": return "Senden und weiter";
            case "Reveal": return "Antwort anzeigen";
            case "Set statistics": return "Serienstatistik";
            case "Set summary": return "Serienübersicht";
            case "Long-term progress": return "Langzeitfortschritt";
            case "Sequence complete. Answer from memory.": return "Sequenz beendet. Aus dem Gedächtnis antworten.";
            case "Starting…": return "Start…";
            case "Set complete.": return "Serie abgeschlossen.";
            case "Set stopped.": return "Serie gestoppt.";
            case "Numbers + letters": return "Zahlen + Buchstaben";
            case "Numbers only": return "Nur Zahlen";
            case "Letters only": return "Nur Buchstaben";
            case "Nothing": return "Keins";
            case "Spaces": return "Leerzeichen";
            case "Hyphens (-)": return "Bindestriche (-)";
            case "Commas (, )": return "Kommas (,)";
            case "Visual": return "Visuell";
            case "Both": return "Beides";
            case "Self-paced": return "Eigenes Tempo";
            case "Progressive (1 → maximum)": return "Progressiv (1 → Maximum)";
            case "Random": return "Zufällig";
            case "Fixed at maximum": return "Fest auf Maximum";
            case "Random: more short": return "Zufällig: mehr kurze";
            case "Random: more long": return "Zufällig: mehr lange";
            case "Random: mostly maximum": return "Zufällig: meist Maximum";
            case "Balanced": return "Ausgewogen";
            case "More letters": return "Mehr Buchstaben";
            case "Fewer letters": return "Weniger Buchstaben";
            case "More numbers": return "Mehr Zahlen";
            case "Fewer numbers": return "Weniger Zahlen";
            case "Ascending": return "Aufsteigend";
            case "Descending": return "Absteigend";
            case "Alphabetical": return "Alphabetisch";
            case "Reverse alphabetical": return "Umgekehrt alphabetisch";
            case "🎙  Hold to speak": return "🎙  Zum Sprechen halten";
            case "🔴 Recording — lift to send": return "🔴 Aufnahme — zum Senden loslassen";
            case "⏳ Transcribing…": return "⏳ Transkription…";
            default: return s;
        }
    }

    private static String ja(String s) {
        switch (s) {
            case "Exercise configuration": return "トレーニング設定";
            case "Presentation language": return "出力言語";
            case "Voice-answer language": return "音声入力言語";
            case "Items": return "項目";
            case "Maximum characters": return "最大文字数";
            case "Number range": return "数値範囲";
            case "Numbers in mixed exercises": return "混合問題の数字比率";
            case "Letter case": return "大文字・小文字";
            case "Number answer order": return "数字の順序";
            case "Letter answer order": return "文字の順序";
            case "Answer separator": return "回答区切り";
            case "Exercises in this set": return "セット内の問題数";
            case "Presentation interval": return "表示間隔";
            case "Presentation mode": return "表示モード";
            case "Exercise length": return "問題の長さ";
            case "Start exercise set": return "トレーニング開始";
            case "Stop set": return "停止";
            case "Current exercise": return "現在の問題";
            case "Ready": return "準備完了";
            case "Configure the set and start.": return "設定して開始してください。";
            case "Next item": return "次の項目";
            case "Finish sequence": return "シーケンス終了";
            case "Enter sorted answer": return "並べ替えた回答を入力";
            case "Submit and continue": return "送信して続行";
            case "Reveal": return "答えを表示";
            case "Set statistics": return "セット統計";
            case "Set summary": return "セット概要";
            case "Long-term progress": return "長期進捗";
            case "Sequence complete. Answer from memory.": return "シーケンス終了。記憶から回答してください。";
            case "Starting…": return "開始中…";
            case "Set complete.": return "セット完了。";
            case "Set stopped.": return "セットを停止しました。";
            case "Numbers + letters": return "数字 + 文字";
            case "Numbers only": return "数字のみ";
            case "Letters only": return "文字のみ";
            case "Nothing": return "なし";
            case "Spaces": return "スペース";
            case "Hyphens (-)": return "ハイフン (-)";
            case "Commas (, )": return "カンマ (,)";
            case "Visual": return "表示";
            case "Both": return "両方";
            case "Self-paced": return "自分のペース";
            case "Progressive (1 → maximum)": return "段階的 (1 → 最大)";
            case "Random": return "ランダム";
            case "Fixed at maximum": return "最大で固定";
            case "Random: more short": return "ランダム：短め優先";
            case "Random: more long": return "ランダム：長め優先";
            case "Random: mostly maximum": return "ランダム：最大優先";
            case "Balanced": return "均等";
            case "More letters": return "文字を多く";
            case "Fewer letters": return "文字を少なく";
            case "More numbers": return "数字を多く";
            case "Fewer numbers": return "数字を少なく";
            case "Ascending": return "昇順";
            case "Descending": return "降順";
            case "Alphabetical": return "アルファベット順";
            case "Reverse alphabetical": return "逆アルファベット順";
            case "🎙  Hold to speak": return "🎙  長押しして話す";
            case "🔴 Recording — lift to send": return "🔴 録音中 — 離して送信";
            case "⏳ Transcribing…": return "⏳ 文字起こし中…";
            default: return s;
        }
    }

    private static String translateDynamic(String language, String value) {
        if (language.equals("en")) return value;
        if (value.startsWith("Exercise ") && value.contains(" of ")) {
            String rest = value.substring(9);
            switch (language) {
                case "it": return "Esercizio " + rest.replace(" of ", " di ");
                case "fr": return "Exercice " + rest.replace(" of ", " sur ");
                case "es": return "Ejercicio " + rest.replace(" of ", " de ");
                case "de": return "Übung " + rest.replace(" of ", " von ");
                case "ja": return "問題 " + rest.replace(" of ", " / ");
            }
        }
        if (value.startsWith("Item ") && value.contains(" of ")) {
            String rest = value.substring(5);
            switch (language) {
                case "it": return "Elemento " + rest.replace(" of ", " di ");
                case "fr": return "Élément " + rest.replace(" of ", " sur ");
                case "es": return "Elemento " + rest.replace(" of ", " de ");
                case "de": return "Element " + rest.replace(" of ", " von ");
                case "ja": return "項目 " + rest.replace(" of ", " / ");
            }
        }
        if (value.startsWith("Loading ")) {
            switch (language) {
                case "it": return value.replace("Loading ", "Caricamento ");
                case "fr": return value.replace("Loading ", "Chargement ");
                case "es": return value.replace("Loading ", "Cargando ");
                case "de": return value.replace("Loading ", "Lade ");
                case "ja": return value.replace("Loading ", "読み込み中: ");
            }
        }
        if (value.startsWith("Listening")) {
            switch (language) {
                case "it": return "Ascolto…";
                case "fr": return "Écoute…";
                case "es": return "Escuchando…";
                case "de": return "Höre zu…";
                case "ja": return "音声認識中…";
            }
        }
        if (value.startsWith("No speech") || value.startsWith("No valid")) {
            switch (language) {
                case "it": return "Nessun input vocale valido riconosciuto.";
                case "fr": return "Aucune entrée vocale valide reconnue.";
                case "es": return "No se reconoció una entrada de voz válida.";
                case "de": return "Keine gültige Spracheingabe erkannt.";
                case "ja": return "有効な音声入力を認識できませんでした。";
            }
        }
        return value;
    }
}
