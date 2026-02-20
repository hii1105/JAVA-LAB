//1вариант
//Ввести с консоли n целых чисел. На консоль вывести:Четные и нечетные числа.
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Main2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Введите количество чисел n: ");
        int n = scan.nextInt();

        List<Integer> even_numbers = new ArrayList<>();
        List<Integer> odd_numbers = new ArrayList<>();

        System.out.println("Введите " + n + " целых чисел:");
        for (int i = 0; i < n; i++) {
            int number = scan.nextInt();

            if (number % 2 == 0) {
                even_numbers.add(number);
            } else {
                odd_numbers.add(number);
            }
        }

        System.out.println("\nЧетные числа: " + even_numbers);
        System.out.println("Нечетные числа: " + odd_numbers);

        scan.close();
    }
}