//1 вариант
//Ввести с консоли n-размерность матрицы a[n][n].
//Задать значения элементов матрицы в интервале значений от -n до n с помощью генератора случайных чисел.
//Упорядочить строки (столбцы) матрицы в порядке возрастания значений элементов k-го столбца (строки).
import java.util.Random;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Размер матрицы n = ");
        int n = scan.nextInt();

        int[][] a = new int[n][n];

        System.out.println("\nИсходная матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = rand.nextInt(2 * n + 1) - n; // от -n до n
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("\n1 - сортировка СТРОК по СТОЛБЦУ");
        System.out.println("2 - сортировка СТОЛБЦОВ по СТРОКЕ");
        System.out.print("Введите число: ");
        int choice = scan.nextInt();

        if (choice == 1) {
            System.out.print("Номер столбца (1-" + n + "): ");
            int k = scan.nextInt() - 1;

            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (a[j][k] > a[j + 1][k]) {
                        int[] temp = a[j];
                        a[j] = a[j + 1];
                        a[j + 1] = temp;
                    }
                }
            }
            System.out.println("Строки отсортированы!");

        } else if (choice == 2) {
            System.out.print("Номер строки (1-" + n + "): ");
            int k = scan.nextInt() - 1;

            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (a[k][j] > a[k][j + 1]) {
                        for (int r = 0; r < n; r++) {
                            int temp = a[r][j];
                            a[r][j] = a[r][j + 1];
                            a[r][j + 1] = temp;
                        }
                    }
                }
            }
            System.out.println("Столбцы отсортированы!");
        }

        System.out.println("\nРезультат:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println();
        }

        scan.close();
    }
}