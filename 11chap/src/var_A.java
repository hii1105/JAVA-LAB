//Вариант A
//1 вариант. Ввести строки из файла, записать в список. Вывести строки в файл в обратном порядке.

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class var_A {

    public static void main(String[] args) {
        String inputFileName = "input.txt";
        String outputFileName = "output.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFileName));

            System.out.println("Исходные строки:");
            lines.forEach(System.out::println);

            List<String> reversed = lines.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                Collections.reverse(list);
                                return list;
                            }
                    ));
            Files.write(Paths.get(outputFileName), reversed);

            System.out.println("Строки в обратном порядке:");
            reversed.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }
}