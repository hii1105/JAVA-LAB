package flower;

import java.io.*;

public class Connector {
    public static void saveBouquet(Bouquet bouquet, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(bouquet);
        }
    }

    public static Bouquet loadBouquet(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Bouquet) ois.readObject();
        }
    }

    public static void saveAvailableFlowers(java.util.List<Flower> flowers, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(flowers);
        }
    }

    @SuppressWarnings("unchecked")
    public static java.util.List<Flower> loadAvailableFlowers(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (java.util.List<Flower>) ois.readObject();
        }
    }

    public static void saveAvailableAccessories(java.util.List<Accessory> accessories, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(accessories);
        }
    }

    @SuppressWarnings("unchecked")
    public static java.util.List<Accessory> loadAvailableAccessories(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (java.util.List<Accessory>) ois.readObject();
        }
    }
}