//Вариант B
//Выполнить задания из варианта B гл. 4, сохраняя объекты приложения в одном или нескольких файлах с применением механизма сериализации.
// Объекты могут содержать поля, помеченные как static, а также transient.
// Для изменения информации и извлечения информации в файле создать специальный класс-коннектор с необходимыми для выполнения этих задач методами.
package варБ;

import flower.*;
import flower.Accessory;
import flower.Bouquet;
import flower.Flower;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Bouquet bouquet;
    private static List<Flower> availableFlowers;
    private static List<Accessory> availableAccessories;

    // имена файлов для сериализации
    private static final String BOUQUET_SER = "bouquet.ser";
    private static final String FLOWERS_SER = "flowers.ser";
    private static final String ACCESSORIES_SER = "accessories.ser";

    public static void main(String[] args) {
        loadSavedData();

        if (availableFlowers == null) {
            availableFlowers = Flower.loadFromFile("flowers.txt");
        }
        if (availableAccessories == null) {
            availableAccessories = Accessory.loadFromFile("accessories.txt");
        }
        if (bouquet == null) {
            bouquet = new Bouquet("Мой букет");
        }

        boolean running = true;
        while (running) {
            printMenu();

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Введите число.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1: assembleBouquet(); break;
                case 2: addAccessories(); break;
                case 3: showPrice(); break;
                case 4: findFlowersByStemLength(); break;
                case 5: sortByFreshness(); break;
                case 6: saveAndExit(); running = false; break;
                case 7: saveAll(); break;
                default: System.out.println("Ошибка! Введите число от 1 до 7.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.print("Введите 1, если желаете собрать букет из цветов"
                + "\nВведите 2, если желаете добавить аксессуары к букету"
                + "\nВведите 3, если желаете вывести на консоль стоимость букета"
                + "\nВведите 4, если желаете найти цветы в букете, соответствующие заданному диапазону длин стеблей"
                + "\nВведите 5, если желаете провести сортировку цветов в букете на основе уровня свежести"
                + "\nВведите 6, если желаете выйти"
                + "\nВведите 7, если желаете сохранить состояние"
                + "\nВводите здесь: ");
    }

    private static void assembleBouquet() {
        System.out.println("\nСборка букета:");
        System.out.println("цветы в наличии:");

        for (int i = 0; i < availableFlowers.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + availableFlowers.get(i));
        }

        System.out.println("  " + (availableFlowers.size() + 1) + ". Закончить сборку");

        while (true) {
            System.out.print("\nВыберите номер цветка (0 - отмена): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    System.out.println("  Сборка отменена");
                    break;
                }

                if (choice == availableFlowers.size() + 1) {
                    System.out.println("  Сборка завершена");
                    break;
                }

                if (choice > 0 && choice <= availableFlowers.size()) {
                    Flower selected = availableFlowers.get(choice - 1);
                    bouquet.addFlower(selected);
                } else {
                    System.out.println("Неверный номер");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода");
                scanner.nextLine();
            }
        }
    }

    private static void addAccessories() {
        if (availableAccessories.isEmpty()) {
            System.out.println("\nНет доступных аксессуаров");
            return;
        }

        System.out.println("\nДобавление аксессуаров:");
        System.out.println("аксессуары в наличии:");

        for (int i = 0; i < availableAccessories.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + availableAccessories.get(i));
        }

        System.out.println("  " + (availableAccessories.size() + 1) + ". Закончить добавление");

        while (true) {
            System.out.print("\nВыберите номер аксессуара (0 - отмена): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    System.out.println("  Добавление отменено");
                    break;
                }

                if (choice == availableAccessories.size() + 1) {
                    System.out.println("  Добавление завершено");
                    break;
                }

                if (choice > 0 && choice <= availableAccessories.size()) {
                    Accessory selected = availableAccessories.get(choice - 1);
                    bouquet.addAccessory(selected);
                } else {
                    System.out.println("Неверный номер");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода");
                scanner.nextLine();
            }
        }
    }

    private static void showPrice() {
        System.out.println("\nСтоимость букета:");
        bouquet.printInfo();
    }

    private static void findFlowersByStemLength() {
        if (bouquet.getFlowers().isEmpty()) {
            System.out.println("\nВ букете нет цветов");
            return;
        }

        System.out.println("\nПоиск цветов по длине стебля");

        try {
            System.out.print("Введите минимальную длину: ");
            double min = scanner.nextDouble();
            System.out.print("Введите максимальную длину: ");
            double max = scanner.nextDouble();
            scanner.nextLine();

            List<Flower> found = bouquet.findFlowersByStemLength(min, max);

            if (found.isEmpty()) {
                System.out.println("Цветы не найдены в диапазоне [" + min + ", " + max + "] см");
            } else {
                System.out.println("Найдено цветов: " + found.size());
                for (int i = 0; i < found.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + found.get(i));
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода. Введите числа.");
            scanner.nextLine();
        }
    }

    private static void sortByFreshness() {
        if (bouquet.getFlowers().isEmpty()) {
            System.out.println("\nВ букете нет цветов для сортировки");
            return;
        }

        bouquet.sortByFreshness();

        System.out.println("\nЦветы после сортировки:");
        List<Flower> flowers = bouquet.getFlowers();
        for (int i = 0; i < flowers.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + flowers.get(i));
        }
    }
    private static void saveAll() {
        try {
            Connector.saveBouquet(bouquet, BOUQUET_SER);
            Connector.saveAvailableFlowers(availableFlowers, FLOWERS_SER);
            Connector.saveAvailableAccessories(availableAccessories, ACCESSORIES_SER);
            System.out.println("Все данные сохранены.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    private static void saveAndExit() {
        saveAll();
    }

    private static void loadSavedData() {
        try {
            bouquet = Connector.loadBouquet(BOUQUET_SER);
            System.out.println("Букет загружен из файла.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Не удалось загрузить букет (будет создан новый).");
            bouquet = null;
        }

        try {
            availableFlowers = Connector.loadAvailableFlowers(FLOWERS_SER);
            System.out.println("Список цветов загружен.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Список цветов не загружен (будет загружен из flowers.txt).");
            availableFlowers = null;
        }

        try {
            availableAccessories = Connector.loadAvailableAccessories(ACCESSORIES_SER);
            System.out.println("Список аксессуаров загружен.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Список аксессуаров не загружен (будет загружен из accessories.txt).");
            availableAccessories = null;
        }
    }
}