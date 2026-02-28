package flower;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Flower {
    protected String name;// название цветка
    protected double price; // цена
    protected String color;// цвет
    protected int freshness;// свежесть (1-10, где 10 - самая свежая)
    protected double stemLength;// длина стебля в см

    public Flower(String name, double price, String color, int freshness, double stemLength) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.freshness = freshness;
        this.stemLength = stemLength;
    }

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

                    Flower flower;
                    if (name.toLowerCase().contains("роза")) {
                        flower = new Rose(name, price, color, freshness, stemLength);
                    } else if (name.toLowerCase().contains("тюльпан")) {
                        flower = new Tulip(name, price, color, freshness, stemLength);
                    } else if (name.toLowerCase().contains("лилия")) {
                        flower = new Lily(name, price, color, freshness, stemLength);
                    } else {
                        flower = new Flower(name, price, color, freshness, stemLength);
                    }

                    flowers.add(flower);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата чисел: " + e.getMessage());
        }

        return flowers;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getColor() { return color; }
    public int getFreshness() { return freshness; }
    public double getStemLength() { return stemLength; }

    @Override
    public String toString() {
        return String.format("%s: цена=%.2f, цвет=%s, свежесть=%d, длина=%.1fсм",
                name, price, color, freshness, stemLength);
    }
}

