//1 вариант
//Вывести на экран таблицу умножения
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите число для таблицы умножения: ");
        int number = scanner.nextInt();

        int factor = 1;

        System.out.println("Таблица умножения на " + number + ":");

        while (factor <= 10) {
            int result = number * factor;
            System.out.println(number + " x " + factor + " = " + result);
            factor++;
        }

        scanner.close();
    }
}