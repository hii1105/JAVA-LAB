//1 вариант
//Определить класс Полином c коэффициентами типа РациональнаяДробь.
// Объявить массив/список/множество из n полиномов и определить сумму полиномов массива

import java.util.Scanner;

class Polinom {
    private int degree; //степень полинома
    private int Coefficients[][];

    public Polinom(int degree) {
        this.degree = degree;
        this.Coefficients = new int [this.degree + 1][2];
        for (int i = 0; i <= this.degree; i++) {
            this.Coefficients[i][0] = 0; //числитель
            this.Coefficients[i][1] = 1; //знаменатель
        }
    }

    public int getDegree() {
        return this.degree;
    }

    public void setPolinom(Scanner scanner, int degree) {
        this.degree = degree;
        this.Coefficients = new int [degree + 1][2];
        for (int i = 0; i <= degree; i++) {
            System.out.print("\n"+i+"-ый коэффициент");
            System.out.print("\nВведите числитель: ");
            int numerator = scanner.nextInt();
            this.Coefficients[i][0] = numerator;
            System.out.print("Введите знаменатель: ");
            int denominator = scanner.nextInt();
            this.Coefficients[i][1] = denominator;
        }
    }

    public void getPolinom() {
        System.out.print("\nСтепень полинома: " + this.degree);
        for (int i = 0; i <= degree; i++) {
            System.out.print("\nКоэффициент " + i + "-го члена полинома: ");
            if (this.Coefficients[i][0] == 0) System.out.print("0");
            else System.out.print(this.Coefficients[i][0] + "/"
                    + this.Coefficients[i][1]);
        }
    }

    public void Show() {
        System.out.println();
        if (this.Coefficients[0][0] != 0) System.out.print(+this.Coefficients[0][0] + "/"
                + this.Coefficients[0][1]);
        if (this.degree != 0) for (int i = 1; i <= degree; i++) {
            System.out.print(" + ");
            if (this.Coefficients[i][0] == this.Coefficients[i][1])
                System.out.print(this.Coefficients[i][0] + "*x^" + i);
            else System.out.print("(" + this.Coefficients[i][0] + "/"
                    + this.Coefficients[i][1] + ")*x^" + i);
        }
    }

    public Polinom Addition(Polinom P0) {
        Polinom Result;
        if (this.degree > P0.getDegree()) {
            Result = new Polinom(this.degree);
            for (int i = 0; i <= P0.getDegree(); i++) {
                Result.Coefficients[i][0] = this.Coefficients[i][0] * P0.Coefficients[i][1] +
                        P0.Coefficients[i][0] * this.Coefficients[i][1];
                Result.Coefficients[i][1] = this.Coefficients[i][1] * P0.Coefficients[i][1];
                for (int j = Result.Coefficients[i][1]; j > 0; j--) {
                    if (Result.Coefficients[i][0] % j == 0 && Result.Coefficients[i][1] % j == 0) {
                        Result.Coefficients[i][0] /= j;
                        Result.Coefficients[i][1] /= j;
                    }
                }
            }
            for (int i = P0.getDegree() + 1; i <= this.degree; i++) {
                Result.Coefficients[i][0] = this.Coefficients[i][0];
                Result.Coefficients[i][1] = this.Coefficients[i][1];
            }
        }
        else{
            Result = new Polinom(P0.getDegree());
            for (int i = 0; i <= this.degree; i++) {
                Result.Coefficients[i][0] = this.Coefficients[i][0] * P0.Coefficients[i][1] +
                        P0.Coefficients[i][0] * this.Coefficients[i][1];
                Result.Coefficients[i][1] = this.Coefficients[i][1] * P0.Coefficients[i][1];
                for (int j = Result.Coefficients[i][1]; j > 0; j--) {
                    if (Result.Coefficients[i][0] % j == 0 && Result.Coefficients[i][1] % j == 0) {
                        Result.Coefficients[i][0] /= j;
                        Result.Coefficients[i][1] /= j;
                    }
                }
            }
            for (int i = this.degree + 1; i <= P0.getDegree(); i++) {
                Result.Coefficients[i][0] = P0.Coefficients[i][0];
                Result.Coefficients[i][1] = P0.Coefficients[i][1];
            }
        }
        return Result;
    }
}

public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // создаем первый полином
        System.out.print("\nВведите степень первого полинома: ");
        int degree1 = scanner.nextInt();
        Polinom p1 = new Polinom(degree1);
        p1.setPolinom(scanner, degree1);

        System.out.println("\nПервый полином:");
        p1.getPolinom();
        System.out.print("\nВ виде выражения: ");
        p1.Show();

        // 2
        System.out.print("\n\nВведите степень второго полинома: ");
        int degree2 = scanner.nextInt();
        Polinom p2 = new Polinom(degree2);
        p2.setPolinom(scanner, degree2);

        System.out.println("\nВторой полином:");
        p2.getPolinom();
        System.out.print("\nВ виде выражения: ");
        p2.Show();

        // +
        Polinom sum = p1.Addition(p2);

        System.out.println("\nРезультат сложения:");
        sum.getPolinom();
        System.out.print("\nВ виде выражения: ");
        sum.Show();

        // создаем массив полиномов
        System.out.print("\n\nВведите количество полиномов в массиве: ");
        int n = scanner.nextInt();

        Polinom[] polyArray = new Polinom[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nПолином " + (i+1) + ":");
            System.out.print("Введите степень: ");
            int deg = scanner.nextInt();
            polyArray[i] = new Polinom(deg);
            polyArray[i].setPolinom(scanner, deg);
        }

        // вывод
        System.out.println("\nМассив полиномов:");
        for (int i = 0; i < n; i++) {
            polyArray[i].Show();
        }
        scanner.close();
    }
}