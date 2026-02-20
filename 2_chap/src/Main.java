//1 вариант
//В приведенных ниже заданиях необходимо вывести внизу фамилию разработчика, дату и время получения задания, а также дату и время сдачи задания.
//Добавить комментарии в программы в виде /** комментарий */. В заданиях на числа объект можно создавать в виде массива символов.
//Ввести n чисел с консоли.

//Найти самое короткое и самое длинное число. Вывести найденные числа
//и их длину

import java.util.Scanner;
public class Main {

    public static void enterNumbers() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество целых чисел: ");
        int n = scanner.nextInt();

        String[] numbers = new String[n];

        System.out.print("Введите целые числа через пробел: ");
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.next();
        }

        findShortAndLongNumbers(numbers);
        scanner.close();
    }

    public static void findShortAndLongNumbers(String[] numbers) {
        if (numbers.length == 0) return;
        String shortest = numbers[0];
        String longest = numbers[0];

        for (String num : numbers) {
            if (num.length() < shortest.length()) {
                shortest = num;
            }
            if (num.length() > longest.length()) {
                longest = num;
            }
        }
        System.out.println("\nСамое короткое число: " + shortest);
        System.out.println("Его длина: " + shortest.length());
        System.out.println("Самое длинное число: " + longest);
        System.out.println("Его длина: " + longest.length());
    }

    public static void main(String[] args) {
        System.out.println("Батуева Сэсэг");
        System.out.println("Дата получения: 31.01.2026 15:00:00");
        System.out.println("Дата сдачи: 10.02.2026 20:00:00");
        System.out.println();

        enterNumbers();
    }
}