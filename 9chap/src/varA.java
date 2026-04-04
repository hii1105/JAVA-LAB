//Вариант A
//В символьном файле находится информация об N числах с плавающей запятой с указанием локали каждого числа отдельно.
// Прочитать информацию из файла.
//Проверить на корректность, то есть являются ли числа числами.
// Преобразовать к числовым значениями вычислить сумму и среднее значение прочитанных чисел.
//Создать собственный класс исключения.
// Предусмотреть обработку исключений, возникающих при нехватке памяти, отсутствии самого файла по заданному адресу,
// отсутствии или некорректности требуемой записи в файле, недопустимом значении числа (выходящим за пределы максимально допустимых значений) и т.д.
import java.io.*;
import java.util.*;

class NumberFormatExceptionCustom extends Exception {

    public NumberFormatExceptionCustom(String message) {
        super(message);
    }

    public NumberFormatExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }
}
public class varA {
    private static final double MAX_VALUE = 1e9;
    private static final double MIN_VALUE = -1e9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к файлу: ");
        String filename = scanner.nextLine();

        try {
            List<Double> numbers = readNumbersFromFile(filename);

            System.out.println("\nПрочитанные числа:");
            for (int i = 0; i < numbers.size(); i++) {
                System.out.println((i + 1) + ". " + numbers.get(i));
            }

            //сумма и среднее
            double sum = calculateSum(numbers);
            double average = calculateAverage(numbers);

            //резы
            System.out.println("Количество чисел: " + numbers.size());
            System.out.println("Сумма: " + sum);
            System.out.println("Среднее значение: " + average);

        } catch (NumberFormatExceptionCustom e) {
            System.out.println("\nОшибка: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            System.out.println("\nОшибка памяти: Недостаточно памяти для обработки файла!");
            System.out.println("Файл слишком большой.");
        } catch (Exception e) {
            System.out.println("\nНепредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    public static List<Double> readNumbersFromFile(String filename)
            throws NumberFormatExceptionCustom {

        List<Double> numbers = new ArrayList<>();
        File file = new File(filename);
        //сущ ли файл
        if (!file.exists()) {
            throw new NumberFormatExceptionCustom(
                    "Файл не найден: " + filename +
                            "\n  Проверьте правильность пути к файлу."
            );
        }

        //можно ли прочитать файл
        if (!file.canRead()) {
            throw new NumberFormatExceptionCustom(
                    "Нет прав на чтение файла: " + filename
            );
        }

        //пустой ли фпйл
        if (file.length() == 0) {
            throw new NumberFormatExceptionCustom(
                    "Файл пуст: " + filename
            );
        }

        //построчное чтение файла
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                //разбиваем строку на число и локаль
                String[] parts = line.split("\\s+");

                //правильный ли формат строки
                if (parts.length < 1) {
                    throw new NumberFormatExceptionCustom(
                            "Строка " + lineNumber + " пуста"
                    );
                }

                String numberStr = parts[0];
                String locale = parts.length > 1 ? parts[1] : "не указана";

                //попытка преобразовать строку в число
                double number = parseNumberWithLocale(numberStr, locale, lineNumber);

                //не выходит ли число за допустимые пределы
                if (number > MAX_VALUE) {
                    throw new NumberFormatExceptionCustom(
                            "Строка " + lineNumber + ": число " + number +
                                    " превышает максимальное допустимое значение " + MAX_VALUE
                    );
                }

                if (number < MIN_VALUE) {
                    throw new NumberFormatExceptionCustom(
                            "Строка " + lineNumber + ": число " + number +
                                    " меньше минимального допустимого значения " + MIN_VALUE
                    );
                }

                //добавляем число в список
                numbers.add(number);
                System.out.println("  Строка " + lineNumber + ": " + number +
                        " (локаль: " + locale + ")");
            }

        } catch (IOException e) {
            throw new NumberFormatExceptionCustom(
                    "Ошибка при чтении файла: " + e.getMessage(), e
            );
        }

        if (numbers.isEmpty()) {
            throw new NumberFormatExceptionCustom(
                    "В файле не найдено ни одного корректного числа"
            );
        }

        return numbers;
    }

    public static double parseNumberWithLocale(String numberStr, String locale, int lineNumber)throws NumberFormatExceptionCustom {

        try {
            numberStr = numberStr.trim();

            switch (locale.toLowerCase()) {
                case "ru":
                case "russian":
                    numberStr = numberStr.replace(',', '.');
                    break;
                case "en":
                case "english":
                case "us":
                    break;
                default:
                    if (numberStr.contains(",") && !numberStr.contains(".")) {
                        numberStr = numberStr.replace(',', '.');
                    }
            }

            //попытка преобразовать в число
            return Double.parseDouble(numberStr);

        } catch (NumberFormatException e) {
            throw new NumberFormatExceptionCustom(
                    "Строка " + lineNumber + ": \"" + numberStr +
                            "\" не является корректным числом (локаль: " + locale + ")"
            );
        }
    }

    public static double calculateSum(List<Double> numbers) {
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum;
    }

    public static double calculateAverage(List<Double> numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }
        return calculateSum(numbers) / numbers.size();
    }
}