//глава8
//вариант 1
//1 задание: В каждом слове текста k-ю букву заменить заданным символом. Если k больше длины слова, корректировку не выполнять.
import java.util.Scanner;

public class Var_A {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите текст: ");
        String text = scanner.nextLine();

        System.out.print("Введите номер буквы для замены (k): ");
        int k = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите символ для замены: ");
        char replacement = scanner.nextLine().charAt(0);

        System.out.println("Исходный текст: " + text);
        System.out.println("Замена " + k + "-й буквы на '" + replacement + "'");
        String result = processText(text, k, replacement);

        System.out.println("Результат: " + result);
        scanner.close();
    }

    public static String processText(String text, int k, char replacement) {
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = processWord(words[i], k, replacement);
        }

        return String.join(" ", words);
    }

    public static String processWord(String word, int k, char replacement) {
        //проверка: Если k больше длины слова, корректировку не выполнять.
        if (k <= word.length()) {
            char[] chars = word.toCharArray();
            chars[k - 1] = replacement;
            return new String(chars);
        } else {
            return word;
        }
    }
}