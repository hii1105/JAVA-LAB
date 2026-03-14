//глава 7
//вариант 1
//С помощью каррирования реализовать функцию сложения двух чисел, функцию проверки строки на регулярное выражение,
// функцию разбиения строки по регулярному выражению.

import java.util.function.Function;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            printMenu();

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ошибка ввода! Введите число.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    additionDemo();
                    break;
                case 2:
                    regexCheckDemo();
                    break;
                case 3:
                    splitDemo();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор! Введите число от 0 до 3.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Сложение двух чисел");
        System.out.println("2. Проверка строки по регулярному выражению");
        System.out.println("3. Разбиение строки по регулярному выражению");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }
    //сложение
    private static void additionDemo() {
        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        System.out.print("Введите первое число: ");
        int num1 = scanner.nextInt();
        System.out.print("Введите второе число: ");
        int num2 = scanner.nextInt();

        System.out.println("Результат: " + add.apply(num1).apply(num2));
    }

    //функция проверки строки на регулярное выражение
    private static void regexCheckDemo() {
        Function<String, Function<String, Boolean>> matches =
                regex -> str -> str.matches(regex);

        System.out.println("Введите регулярное выражение:");
        String regex = scanner.nextLine();

        Function<String, Boolean> checker = matches.apply(regex);

        System.out.println("Введите строку для проверки (или '0' для выхода):");

        while (true) {
            System.out.print("Строка: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("0")) {
                break;
            }
            boolean result = checker.apply(input);
            System.out.println("Результат: " + input + " соответствует шаблону? " + result);
        }
    }

    //функцию разбиения строки по регулярному выражению
    private static void splitDemo() {
        Scanner scanner = new Scanner(System.in);
        Function<String, Function<String, String[]>> split =
                regex -> str -> str.split(regex);

        System.out.print("Введите разделитель: ");
        String delimiter = scanner.nextLine();

        Function<String, String[]> splitter = split.apply(delimiter);

        System.out.print("Введите строку для разбиения: ");
        String text = scanner.nextLine();
        String[] result = splitter.apply(text);

        System.out.println("Результат разбиения:" + Arrays.toString(result));
    }
}