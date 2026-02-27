//вариант 1
//Создать приложение, удовлетворяющее требованиям, приведенным в задании. Наследование применять только в тех заданиях,
//в которых это логически обосновано. Аргументировать принадлежность классу каждого создаваемого метода
// и корректно переопределить для каждого класса методы equals(),hashCode(), toString().
//1.Создать объект класса Текст, используя классы Предложение, Слово.
//Методы: дополнить текст, вывести на консоль текст, заголовок текста.
package варА;

import text.Text;
import text.Sentence;
import text.Word;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Text text = null;
        try {
            text = new Text("text.txt", true);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        boolean running = true;

        while (running) {
            System.out.print("Введите 1, если желаете дополнить текст"
                    + "\nВведите 2, если желаете вывести на консоль заголовок текста"
                    + "\nВведите 3, если желаете вывести на консоль текст"
                    + "\nВведите 4, если желаете выйти"
                    + "\nВводите здесь: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера
            } catch (Exception e) {
                System.out.println("Ошибка ввода! Введите число.");
                scanner.nextLine(); // очистка буфера
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите текст для добавления: ");
                    String additionalText = scanner.nextLine();

                    if (!additionalText.trim().isEmpty()) {
                        text.appendText(additionalText);

                        System.out.println("\nОбновленный текст:");
                        text.printText();
                    } else {
                        System.out.println("Текст не может быть пустым!");
                    }
                    break;

                case 2:
                    text.printTitle();
                    break;

                case 3:
                    text.printText();
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Неверный выбор! Введите число от 1 до 4.");
            }
        }

        scanner.close();
    }
}