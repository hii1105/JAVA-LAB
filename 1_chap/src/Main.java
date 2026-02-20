//1 вариант
// Приветствовать любого пользователя при вводе его имени через командную строку.
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.println("Введите ваше имя:");
        Scanner scan = new Scanner(System.in);
        String name = scan.next();
        System.out.println("Привет, " + name);
        scan.close();
    }
}
