//Вариант B
//1 вар. В кругу стоят N человек, пронумерованных от 1 до N.
// При ведении счета по кругу вычеркивается каждый второй человек, пока не останется один.
//Составить две программы, моделирующие процесс.
// Одна из программ должна использовать класс ArrayList, а вторая — LinkedList. Какая из двух программ работает быстрее? Почему?
import java.util.*;

public class var_B {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = 0;
        boolean valid = false;

        System.out.print("Введите количество человек N: ");
        while (!valid) {
            try {
                N = scanner.nextInt();
                if (N > 0) {
                    valid = true;
                } else {
                    System.out.print("Число должно быть положительным. Попробуйте снова: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Ошибка ввода. Введите целое положительное число: ");
                scanner.next();
            }
        }
        scanner.close();

        //ArrayList
        long startTime = System.nanoTime();
        int lastArrayList = simulateWithArrayList(N);
        long endTime = System.nanoTime();
        long timeArrayList = endTime - startTime;

        //LinkedList
        startTime = System.nanoTime();
        int lastLinkedList = simulateWithLinkedList(N);
        endTime = System.nanoTime();
        long timeLinkedList = endTime - startTime;

        System.out.println("Результат:");
        System.out.println("  ArrayList : " + lastArrayList);
        System.out.println("  LinkedList: " + lastLinkedList);

        System.out.println("\nВремя выполнения:");
        System.out.printf("  ArrayList : %.2f мс%n", timeArrayList / 1_000_000.0);
        System.out.printf("  LinkedList: %.2f мс%n", timeLinkedList / 1_000_000.0);

        System.out.println("\nПротестировав с разными числами, можно сделать вывод, что при маленьких значениях N ArrayList будет быстрее, чем LinkedList, и наоборот, при больших значениях N LinkedList будет быстрее.");
        System.out.println("Так происходит, потому что в ArrayList удаление элемента из середины заставляет сдвигать все элементы справа. Чем больше список, тем дольше каждый сдвиг. В LinkedList удаление элемента через итератор не требует сдвига,а просто переназначаются ссылки соседних узлов.");

    }

    private static int simulateWithArrayList(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        int index = 0;
        while (list.size() > 1) {
            index = (index + 1) % list.size();
            list.remove(index);
        }
        return list.get(0);
    }

    private static int simulateWithLinkedList(int n) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        ListIterator<Integer> it = list.listIterator();
        int count = 0;
        while (list.size() > 1) {
            if (!it.hasNext()) {
                it = list.listIterator();
            }
            it.next();
            count++;
            if (count % 2 == 0) {
                it.remove();
            }
        }
        return list.get(0);
    }
}