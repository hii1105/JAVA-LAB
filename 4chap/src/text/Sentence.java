package text;

import java.util.*;
import java.util.Objects;

public class Sentence {
    private List<Word> words;        // список слов в предложении
    private String ending;            // знак препинания в конце
    public Sentence(String sentence) {
        this.words = new ArrayList<>();
        parseSentence(sentence);
    }

    // разбор строки на слова
    private void parseSentence(String sentence) {
        sentence = sentence.trim();

        // опр знак в конце предложения
        if (sentence.endsWith(".") || sentence.endsWith("!") || sentence.endsWith("?")) {
            ending = sentence.substring(sentence.length() - 1);
            sentence = sentence.substring(0, sentence.length() - 1).trim();
        } else {
            ending = "";
        }

        // разб на слова (разделители - пробелы и знаки препинания)
        String[] wordArray = sentence.split("[\\s,;:-]+");
        for (String w : wordArray) {
            if (!w.isEmpty()) {
                words.add(new Word(w));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(words, sentence.words) &&
                Objects.equals(ending, sentence.ending);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words, ending);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(words.get(i).toString());
        }
        sb.append(ending);
        return sb.toString();
    }
}