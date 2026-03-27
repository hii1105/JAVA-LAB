//глава8
//вариант 1
//2 задание: Создать программу обработки текста учебника по программированию с использованием классов:
// Символ, Слово, Предложение, Абзац, Лексема, Листинг, Знак препинания и др.
// Во всех задачах с формированием текста заменять табуляции и последовательности пробелов одним пробелом.
//Предварительно текст следует разобрать на составные части, выполнить одно из перечисленных ниже заданий и вывести полученный результат.
//1. Найти наибольшее количество предложений текста, в которых есть одинаковые слова.

import java.util.*;
class Symbol {
    protected char value;

    public Symbol(char value) {
        this.value = value;
    }

    public char getValue() { return value; }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

class Punctuation extends Symbol {
    public Punctuation(char value) {
        super(value);
    }
}

class Word {
    private String text;
    private List<Symbol> letters;

    public Word(String text) {
        this.text = text;
        this.letters = new ArrayList<>();
        for (char c : text.toCharArray()) {
            letters.add(new Symbol(c));
        }
    }

    public String getText() { return text; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return text.equalsIgnoreCase(word.text); // сравниваем без учёта регистра
    }

    @Override
    public int hashCode() {
        return text.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}

class Lexeme {
    private Object value; // Word или Punctuation

    public Lexeme(Word word) {
        this.value = word;
    }

    public Lexeme(Punctuation punct) {
        this.value = punct;
    }

    public boolean isWord() {
        return value instanceof Word;
    }

    public Word getWord() {
        return (Word) value;
    }

    public Punctuation getPunctuation() {
        return (Punctuation) value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

class Sentence {
    private List<Lexeme> lexemes;

    public Sentence(String sentenceText) {
        this.lexemes = new ArrayList<>();
        parseSentence(sentenceText);
    }

    //разбираем строку предложения на лексемы
    private void parseSentence(String sentence) {
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);

            //если буква или цифра — часть слова
            if (Character.isLetterOrDigit(c)) {
                currentWord.append(c);
            }
            //пробел — разделитель слов
            else if (c == ' ') {
                if (currentWord.length() > 0) {
                    lexemes.add(new Lexeme(new Word(currentWord.toString())));
                    currentWord.setLength(0);
                }
            }
            //знак препинания
            else {
                if (currentWord.length() > 0) {
                    lexemes.add(new Lexeme(new Word(currentWord.toString())));
                    currentWord.setLength(0);
                }
                lexemes.add(new Lexeme(new Punctuation(c)));
            }
        }

        //добав последнее слово, если есть
        if (currentWord.length() > 0) {
            lexemes.add(new Lexeme(new Word(currentWord.toString())));
        }
    }

    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (Lexeme lex : lexemes) {
            if (lex.isWord()) {
                words.add(lex.getWord());
            }
        }
        return words;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Lexeme lex : lexemes) {
            sb.append(lex.toString());
            // после слова ставим пробел, после знака препинания – нет
            if (lex.isWord()) {
                sb.append(' ');
            }
        }
        return sb.toString().trim();
    }
}

class Paragraph {
    private List<Sentence> sentences;

    public Paragraph(String paragraphText) {
        this.sentences = new ArrayList<>();
        parseParagraph(paragraphText);
    }

    //разбиваем текст абзаца на предложения по . ! ?
    private void parseParagraph(String text) {
        //разделяем по .!? (с сохранением разделителя)
        String[] parts = text.split("(?<=[.!?])\\s*");
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                sentences.add(new Sentence(part.trim()));
            }
        }
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence s : sentences) {
            sb.append(s.toString()).append(' ');
        }
        return sb.toString();
    }
}

class Listing {
    private String code;

    public Listing(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "[ЛИСТИНГ]\n" + code + "\n[/ЛИСТИНГ]";
    }
}

class TextbookText {
    private List<Object> elements;

    public TextbookText(String fullText) {
        this.elements = new ArrayList<>();
        parseText(fullText);
    }

    private void parseText(String text) {
        text = text.replaceAll("\\t+", " ");
        text = text.replaceAll("\\s+", " ");

        //на абзацы
        String[] paragraphs = text.split("\\n\\s*\\n");

        for (String p : paragraphs) {
            if (!p.trim().isEmpty()) {
                // Если встречается код — создаём Listing, иначе Paragraph
                if (p.contains("public class") || p.contains("{") || p.contains("//")) {
                    elements.add(new Listing(p));
                } else {
                    elements.add(new Paragraph(p));
                }
            }
        }
    }

    public List<Paragraph> getParagraphs() {
        List<Paragraph> result = new ArrayList<>();
        for (Object elem : elements) {
            if (elem instanceof Paragraph) {
                result.add((Paragraph) elem);
            }
        }
        return result;
    }

    public List<Sentence> getAllSentences() {
        List<Sentence> all = new ArrayList<>();
        for (Paragraph p : getParagraphs()) {
            all.addAll(p.getSentences());
        }
        return all;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object elem : elements) {
            sb.append(elem.toString()).append("\n\n");
        }
        return sb.toString();
    }
}

public class Var_B {
    public static void main(String[] args) {
        String text =
                "Класс String поддерживает несколько конструкторов, например: String()." +
                        "Эти конструкторы используются для создания объектов класса String на основе их инициализации значениями из массива типа char, byte и др." +
                        "Методом reverse() можно быстро изменить порядок символов в объекте.";
        System.out.println("Исходный текст:");
        System.out.println(text);
        System.out.println();

        //заменяем табуляции и множественные пробелы одним пробелом
        String normalized = text.replaceAll("\\t+", " ");
        normalized = normalized.replaceAll("\\s+", " ");

        System.out.println("Текст после изменения:");
        System.out.println(normalized);
        System.out.println();

        //разбираем на составные части
        TextbookText textbook = new TextbookText(normalized);

        //получаем все предложения
        List<Sentence> sentences = textbook.getAllSentences();
        System.out.println("Количество предложений: " + sentences.size());

        System.out.println("\nПредложения:");
        for (int i = 0; i < sentences.size(); i++) {
            System.out.println((i + 1) + ". " + sentences.get(i));
        }

        //список номеров предложений, где встречается одно слово
        Map<Word, List<Integer>> wordToSentenceNumbers = new HashMap<>();

        for (int i = 0; i < sentences.size(); i++) {
            Sentence sentence = sentences.get(i);
            Set<Word> uniqueWords = new HashSet<>(sentence.getWords());

            for (Word w : uniqueWords) {
                if (!wordToSentenceNumbers.containsKey(w)) {
                    wordToSentenceNumbers.put(w, new ArrayList<>());
                }
                wordToSentenceNumbers.get(w).add(i + 1); // нумерация с 1
            }
        }

        //находим наибольшее количество предложений текста, в которых есть одинаковые слова.
        Word bestWord = null;
        int maxCount = 0;
        List<Integer> bestIndices = null;

        for (Map.Entry<Word, List<Integer>> entry : wordToSentenceNumbers.entrySet()) {
            int count = entry.getValue().size();
            if (count > maxCount) {
                maxCount = count;
                bestWord = entry.getKey();
                bestIndices = entry.getValue();
            }
        }

        if (bestWord != null) {
            System.out.println("Слово \"" + bestWord + "\" встречается в " + maxCount + " предложениях:");
            System.out.print("Номера предложений: ");
            for (int i = 0; i < bestIndices.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(bestIndices.get(i));
            }
            System.out.println();

            System.out.println("\nПредложения, содержащие это слово:");
            for (int idx : bestIndices) {
                System.out.println(idx + ". " + sentences.get(idx - 1));
            }
        } else {
            System.out.println("В тексте нет слов.");
        }
    }
}