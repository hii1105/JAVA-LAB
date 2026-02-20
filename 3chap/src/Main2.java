//1 вариант
//Реализовать методы сложения, вычитания, умножения и деления объектов (для тех классов, объекты которых могут поддерживать арифметические действия).
//1. Определить класс Дробь (Рациональная Дробь) в виде пары чисел m и n.
//Объявить и инициализировать массив из k дробей, ввести/вывести значения для массива дробей.
// Создать массив/список/множество объектов и передать его в метод,
// который изменяет каждый элемент массива с четным индексом путем добавления следующего за ним элемента.
import java.util.Scanner;

class RationalFraction {
    private int numerator;
    private int denominator;

    // конструктор
    public RationalFraction(int numerator, int denominator) {
        if (denominator == 0) {
            System.out.println("Ошибка! Знаменатель не может быть 0");
            this.numerator = 0;
            this.denominator = 1;
        } else {
            this.numerator = numerator;
            this.denominator = denominator;
            normalize();
        }
    }

    private void normalize() {
        int a = Math.abs(numerator);
        int b = Math.abs(denominator);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        int gcd = a;

        if (gcd > 1) {
            numerator /= gcd;
            denominator /= gcd;
        }

        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    //массив объектов
    public static RationalFraction[] objectArray(int number) {
        RationalFraction[] array = new RationalFraction[number];
        for (int i = 0; i < number; i++) {
            array[i] = new RationalFraction(0, 1);
        }
        return array;
    }

    //toString
    @Override
    public String toString() {
        if (denominator == 1) {
            return numerator + "";
        } else {
            return numerator + "/" + denominator;
        }
    }

    //+
    public RationalFraction Addition(RationalFraction Number) {
        int newNumerator = this.numerator * Number.denominator +
                Number.numerator * this.denominator;
        int newDenominator = this.denominator * Number.denominator;
        return new RationalFraction(newNumerator, newDenominator);
    }

    //-
    public RationalFraction Subtraction(RationalFraction Number) {
        int newNumerator = this.numerator * Number.denominator -
                Number.numerator * this.denominator;
        int newDenominator = this.denominator * Number.denominator;
        return new RationalFraction(newNumerator, newDenominator);
    }

    //*
    public RationalFraction Multiplication(RationalFraction Number) {
        int newNumerator = this.numerator * Number.numerator;
        int newDenominator = this.denominator * Number.denominator;
        return new RationalFraction(newNumerator, newDenominator);
    }

    //:
    public RationalFraction Division(RationalFraction Number) {
        if (Number.numerator == 0) {
            System.out.println("Ошибка! Деление на 0");
            return new RationalFraction(0, 1);
        }
        int newNumerator = this.numerator * Number.denominator;
        int newDenominator = this.denominator * Number.numerator;
        return new RationalFraction(newNumerator, newDenominator);
    }


    // изменение элементов с четным индексом
    public static void Change(RationalFraction[] Array) {
        System.out.println("\nИзменение элементов с четными индексами");
        for (int i = 0; i < Array.length - 1; i++) {
            if (i % 2 == 0) {
                RationalFraction oldValue = new RationalFraction(Array[i].numerator, Array[i].denominator);
                RationalFraction sum = Array[i].Addition(Array[i + 1]);
                Array[i].numerator = sum.numerator;
                Array[i].denominator = sum.denominator;
                System.out.println("  [" + i + "] " + oldValue + " + " + Array[i+1] + " = " + Array[i]);
            }
        }
        if (Array.length % 2 == 1 && Array.length > 0) {
            int lastIndex = Array.length - 1;
            if (lastIndex % 2 == 0) {
                System.out.println("  [" + lastIndex + "] нет следующего элемента");
            }
        }
    }

    public static void inputArray(RationalFraction[] array, Scanner scanner) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("\nДробь " + (i + 1) + ":");
            System.out.print("  Числитель: ");
            int num = scanner.nextInt();
            System.out.print("  Знаменатель: ");
            int den = scanner.nextInt();
            array[i] = new RationalFraction(num, den);
        }
    }

    public static void printArray(RationalFraction[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("  [" + i + "] " + array[i]);
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество дробей k: ");
        int k = scanner.nextInt();

        RationalFraction[] fractions = RationalFraction.objectArray(k);

        RationalFraction.inputArray(fractions, scanner);

        System.out.println("\nИсходный массив:");
        RationalFraction.printArray(fractions);

        RationalFraction.Change(fractions);

        System.out.println("\nИтоговый массив:");
        RationalFraction.printArray(fractions);

        if (k >= 2) {
            System.out.println("\nАрифметические действия:");
            System.out.println("f1 = " + fractions[0]);
            System.out.println("f2 = " + fractions[1]);
            System.out.println("f1 + f2 = " + fractions[0].Addition(fractions[1]));
            System.out.println("f1 - f2 = " + fractions[0].Subtraction(fractions[1]));
            System.out.println("f1 * f2 = " + fractions[0].Multiplication(fractions[1]));
            System.out.println("f1 / f2 = " + fractions[0].Division(fractions[1]));
        }

        scanner.close();
    }
}