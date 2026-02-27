package ru.esstu.maven;

import java.util.Random;

public class MathUtils {

    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал отрицательного числа не определен");
        }

        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            throw new ArithmeticException("НОК для нуля не определено");
        }
        return Math.abs(a * b) / gcd(a, b);
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int randomInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min не может быть больше max");
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}