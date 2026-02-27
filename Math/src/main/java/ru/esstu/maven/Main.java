//1 вариант
// Библиотека для работы с математическими операциями.
//  Реализовать методы для вычисления факториала, нахождения НОД и НОК, проверки числа на простоту, генерации случайных чисел в заданном диапазоне.
package ru.esstu.maven;

import lombok.extern.java.Log;

import java.util.Scanner;

@Log
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            System.out.print("Выберите операцию (0-6): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    break;
                }

                processChoice(choice);

            } catch (Exception e) {
                log.warning("Ошибка ввода: " + e.getMessage());
                System.out.println("Ошибка! Введите число от 0 до 6");
                scanner.nextLine();
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("1. Вычислить факториал");
        System.out.println("2. Найти НОД двух чисел");
        System.out.println("3. Найти НОК двух чисел");
        System.out.println("4. Проверить число на простоту");
        System.out.println("5. Сгенерировать случайное число");
        System.out.println("0. Выход");
    }

    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                demoFactorial();
                break;
            case 2:
                demoGcd();
                break;
            case 3:
                demoLcm();
                break;
            case 4:
                demoPrime();
                break;
            case 5:
                demoRandom();
                break;
            default:
                System.out.println("Неверный выбор! Введите число от 0 до 6");
        }
    }

    private static void demoFactorial() {
        System.out.print("Введите число (0-20): ");
        try {
            int n = scanner.nextInt();
            long result = MathUtils.factorial(n);

            Result res = new Result("factorial", String.valueOf(n), String.valueOf(result));
            printResult(res);

            log.info("Вычислен factorial(" + n + ") = " + result);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            log.warning("Ошибка factorial: " + e.getMessage());
        }
    }

    private static void demoGcd() {
        System.out.print("Введите первое число: ");
        int a = scanner.nextInt();
        System.out.print("Введите второе число: ");
        int b = scanner.nextInt();

        int result = MathUtils.gcd(a, b);
        Result res = new Result("НОД", a + " и " + b, String.valueOf(result));
        printResult(res);

        log.info("Вычислен НОД(" + a + ", " + b + ") = " + result);
    }

    private static void demoLcm() {
        System.out.print("Введите первое число: ");
        int a = scanner.nextInt();
        System.out.print("Введите второе число: ");
        int b = scanner.nextInt();

        try {
            int result = MathUtils.lcm(a, b);
            Result res = new Result("НОК", a + " и " + b, String.valueOf(result));
            printResult(res);

            log.info("Вычислен НОК(" + a + ", " + b + ") = " + result);

        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
            log.warning("Ошибка НОК: " + e.getMessage());
        }
    }

    private static void demoPrime() {
        System.out.print("Введите число: ");
        int n = scanner.nextInt();

        boolean isPrime = MathUtils.isPrime(n);
        String result = isPrime ? "простое" : "не простое";

        Result res = new Result("Проверка на простоту", String.valueOf(n), result);
        printResult(res);

        log.info("Проверка на простоту(" + n + ") = " + isPrime);
    }

    private static void demoRandom() {
        System.out.print("Введите мин значение: ");
        int min = scanner.nextInt();
        System.out.print("Введите макс значение: ");
        int max = scanner.nextInt();

        try {
            int result = MathUtils.randomInRange(min, max);
            Result res = new Result("random", "[" + min + "," + max + "]", String.valueOf(result));
            printResult(res);

            log.info("Сгенерировано случайное число " + result + " в диапазоне [" + min + "," + max + "]");

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            log.warning("Ошибка random: " + e.getMessage());
        }
    }

    private static void printResult(Result result) {
        System.out.printf("Операция: %-20s \n", result.getOperation());
        System.out.printf("Входные данные: %-14s\n", result.getInput());
        System.out.printf("Результат: %-19s\n", result.getOutput());
    }
}