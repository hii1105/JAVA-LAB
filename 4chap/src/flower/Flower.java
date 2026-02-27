package flower;

import java.util.*;
import java.io.*;

public class Flower {
    private String name;           // название цветка
    private double price;           // цена
    private String color;           // цвет
    private int freshness;          // свежесть (1-10, где 10 - самая свежая)
    private double stemLength;      // длина стебля в см

    public Flower(String name, double price, String color, int freshness, double stemLength) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.freshness = freshness;
        this.stemLength = stemLength;
    }

    //конструктор для чтения цветов из файла
    public static List<Flower> loadFromFile(String filename) {
        List<Flower> flowers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    String color = parts[2].trim();
                    int freshness = Integer.parseInt(parts[3].trim());
                    double stemLength = Double.parseDouble(parts[4].trim());

                    flowers.add(new Flower(name, price, color, freshness, stemLength));
                }
            }
            System.out.println("Загружено " + flowers.size() + " цветов из файла " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата чисел: " + e.getMessage());
        }

        return flowers;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public int getFreshness() {
        return freshness;
    }

    public double getStemLength() {
        return stemLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return Double.compare(flower.price, price) == 0 &&
                freshness == flower.freshness &&
                Double.compare(flower.stemLength, stemLength) == 0 &&
                Objects.equals(name, flower.name) &&
                Objects.equals(color, flower.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, color, freshness, stemLength);
    }

    @Override
    public String toString() {
        return String.format("%s: цена=%.2f, цвет=%s, свежесть=%d, длина=%.1fсм",
                name, price, color, freshness, stemLength);
    }
}