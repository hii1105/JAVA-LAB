package flower;

import java.io.Serializable;
import java.util.*;

public class Bouquet implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Flower> flowers;
    private List<Accessory> accessories;
    private String name;

    public Bouquet(String name) {
        this.name = name;
        this.flowers = new ArrayList<>();
        this.accessories = new ArrayList<>();
    }
    public void addFlower(Flower flower) {
        flowers.add(flower);
        System.out.println("Добавлен: " + flower.getName());
    }

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
        System.out.println("Добавлен: " + accessory.getName());
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public double calculateTotalPrice() {
        double total = 0;

        for (Flower flower : flowers) {
            total += flower.getPrice();
        }

        for (Accessory accessory : accessories) {
            total += accessory.getPrice();
        }

        return total;
    }

    //сортировка цветов по свежести (от самых свежих)
    public void sortByFreshness() {
        Collections.sort(flowers, new Comparator<Flower>() {
            @Override
            public int compare(Flower f1, Flower f2) {
                //по убыванию свежести
                return Integer.compare(f2.getFreshness(), f1.getFreshness());
            }
        });
        System.out.println("Цветы отсортированы по свежести (от самых свежих)");
    }

    public List<Flower> findFlowersByStemLength(double min, double max) {
        List<Flower> result = new ArrayList<>();

        for (Flower flower : flowers) {
            double length = flower.getStemLength();
            if (length >= min && length <= max) {
                result.add(flower);
            }
        }

        return result;
    }

    //инфа о букете
    public void printInfo() {
        System.out.println("\nБУКЕТ: " + name);

        System.out.println("\nЦветы (" + flowers.size() + " шт.)");
        if (flowers.isEmpty()) {
            System.out.println("  Нет цветов");
        } else {
            for (int i = 0; i < flowers.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + flowers.get(i));
            }
        }

        System.out.println("\nАксессуары (" + accessories.size() + " шт.)");
        if (accessories.isEmpty()) {
            System.out.println("  Нет аксессуаров");
        } else {
            for (int i = 0; i < accessories.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + accessories.get(i));
            }
        }

        System.out.println("\nСтоимость: " + calculateTotalPrice() + " руб.");
    }
}