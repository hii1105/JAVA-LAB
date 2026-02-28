package flower;

import java.util.*;
import java.io.*;

public class Accessory {
    private String name;      // название аксессуара
    private double price;     // цена

    public Accessory(String name, double price) {
        this.name = name;
        this.price = price;
    }

    //конструктор для чтения аксессуаров из файла
    public static List<Accessory> loadFromFile(String filename) {
        List<Accessory> accessories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    accessories.add(new Accessory(name, price));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата чисел: " + e.getMessage());
        }

        return accessories;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accessory accessory = (Accessory) o;
        return Double.compare(accessory.price, price) == 0 &&
                Objects.equals(name, accessory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f руб.)", name, price);
    }
}