package text;

import java.util.Objects;

public class Word {
    private String word;


    public Word(String word) {
        // убираем знаки препинания в начале и конце слова
        this.word = word.replaceAll("^[\\.,!?;:\\-\\s]+|[\\.,!?;:\\-\\s]+$", "");
    }

    // Переопределение методов Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word.toLowerCase(), word1.word.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(word.toLowerCase());
    }

    @Override
    public String toString() {
        return word;
    }
}