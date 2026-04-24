//Вариант A
//В следующих заданиях требуется ввести последовательность строк из текстового потока и выполнить указанные действия.
// При этом могут рассматриваться два варианта:
//• каждая строка состоит из одного слова;
//• каждая строка состоит из нескольких слов.
//Имена входного и выходного файлов, а также абсолютный путь к ним могут быть введены как параметры командной строки или храниться в файле.
//1 вариант: В каждой строке найти и удалить заданную подстроку.

import java.io.*;
import java.util.Scanner;

public class var_A {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputFile;
        String outputFile;
        String substringToRemove;

        if (args.length >= 2) {
            inputFile = args[0];
            outputFile = args[1];
            substringToRemove = (args.length >= 3) ? args[2] : "";
            System.out.println("Используются параметры командной строки.");
        } else {
            System.out.print("Введите путь к входному файлу: ");
            inputFile = scanner.nextLine();
            System.out.print("Введите путь к выходному файлу: ");
            outputFile = scanner.nextLine();
            System.out.print("Введите подстроку для удаления: ");
            substringToRemove = scanner.nextLine();
        }

        try {
            processFile(inputFile, outputFile, substringToRemove);
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлами: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    public static void processFile(String inputFile, String outputFile, String substring)
            throws IOException {
        if (substring == null) {
            throw new IllegalArgumentException("Подстрока не может быть null.");
        }

        //try-with-resources для автомат закрытия потоков
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int lineNumber = 0;
            int totalRemovals = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                String newLine = line.replace(substring, "");

                writer.write(newLine);
                writer.newLine();

                int occurrences = countOccurrences(line, substring);
                totalRemovals += occurrences;
                if (occurrences > 0) {
                    System.out.println("Строка " + lineNumber + ": удалено " + occurrences + " вхождений.");
                }
            }

            System.out.println("Всего обработано строк: " + lineNumber);
            System.out.println("Всего удалено вхождений подстроки: " + totalRemovals);

        } catch (FileNotFoundException e) {
            throw new IOException("Входной файл не найден: " + inputFile, e);
        } catch (SecurityException e) {
            throw new IOException("Нет прав доступа к файлу: " + e.getMessage(), e);
        }
    }

    private static int countOccurrences(String text, String substring) {
        if (substring.isEmpty()) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }
}