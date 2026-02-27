package text;

import java.util.*;
import java.io.*;
import java.util.Objects;

public class Text {
    private String title;              // заголовок текста
    private List<Sentence> sentences;   // список предложений

    public Text(String filename, boolean isFile) throws IOException {
        this.sentences = new ArrayList<>();
        readFromFile(filename);
    }

    private void readFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (firstLine) {
                    this.title = line;
                    firstLine = false;
                } else {
                    String[] sentenceArray = line.split("(?<=[.!?])\\s*");
                    for (String s : sentenceArray) {
                        if (!s.trim().isEmpty()) {
                            sentences.add(new Sentence(s));
                        }
                    }
                }
            }
        }
    }

    // добавить текст
    public void appendText(String additionalText) {
        String[] sentenceArray = additionalText.split("(?<=[.!?])\\s*");
        for (String s : sentenceArray) {
            if (!s.trim().isEmpty()) {
                sentences.add(new Sentence(s));
            }
        }
    }
    // ВЫВЕСТИ ЗАГОЛОВОК ТЕКСТА
    public void printTitle() {
        System.out.println("\nЗаголовок текста:");
        System.out.println(title);
    }

    // ВЫВЕСТИ ТЕКСТ НА КОНСОЛЬ
    public void printText() {
        System.out.println("\nТекст:");
        for (Sentence sentence : sentences) {
            System.out.println(sentence.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return Objects.equals(title, text.title) &&
                Objects.equals(sentences, text.sentences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, sentences);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (Sentence s : sentences) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}