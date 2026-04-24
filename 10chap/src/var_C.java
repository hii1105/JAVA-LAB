//Вариант С
//При выполнении следующих заданий для вывода результатов создавать новую директорию и файл средствами класса File.
//1 вар. Создать и заполнить файл случайными целыми числами. Отсортировать содержимое файла по возрастанию.
import java.io.*;
import java.util.*;

public class var_C {

    public static void main(String[] args) {
        File dir = new File("file");

        if (!dir.exists()) {
            boolean created = dir.mkdir();
            if (created) {
                System.out.println("Папка успешно создана");
            } else {
                System.out.println("Не удалось создать папку");
                return;
            }
        } else {
            System.out.println("Папка уже существует");
        }

        File file = new File(dir, "numbers.txt");
        fillFileWithRandomNumbers(file, 10);

        List<Integer> numbers = readNumbersFromFile(file);
        System.out.println("Исходные числа: " + numbers);

        Collections.sort(numbers);
        writeNumbersToFile(file, numbers);

        List<Integer> sortedNumbers = readNumbersFromFile(file);
        System.out.println("Отсортированные числа: " + sortedNumbers);
    }

    private static void fillFileWithRandomNumbers(File file, int count) {
        Random random = new Random();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < count; i++) {
                int num = random.nextInt(201) - 100; //-100 до 100
                writer.println(num);
            }
            System.out.println("Файл заполнен " + count + " случайными числами.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private static List<Integer> readNumbersFromFile(File file) {
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int num = Integer.parseInt(line.trim());
                    numbers.add(num);
                } catch (NumberFormatException e) {
                    System.out.println("Предупреждение: пропущена некорректная строка: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return numbers;
    }

    private static void writeNumbersToFile(File file, List<Integer> numbers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int num : numbers) {
                writer.println(num);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}