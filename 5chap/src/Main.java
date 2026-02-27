//Вариант А, вариант 1
//1. Создать класс Notepad с внутренним классом или классами, с помощью
// объектов которого могут храниться несколько записей на одну дату.
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Notepad {
    private int day;
    private int month;
    private int year;
    private List<String> notes;

    public Notepad(int day, int month, int year, String note) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.notes = new ArrayList<>();
        this.notes.add(note);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setNotes(String note) {
        this.notes.add(note);
    }

    public void addNotes(Scanner scanner) {
        System.out.print("Введите запись: ");
        scanner.nextLine(); // очистка буфера
        String note = scanner.nextLine();
        this.notes.add(note);
        System.out.println("Запись добавлена!");
    }

    public void ShowNotes() {
        System.out.println("Записи на " + day + "." + month + "." + year + ":");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + notes.get(i));
        }
    }

    public void Show() {
        System.out.println("\nДата: " + day + "." + month + "." + year);
        System.out.println("Количество записей: " + notes.size());
        for (int i = 0; i < notes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + notes.get(i));
        }
    }
}

class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            ArrayList<Notepad> notepads = new ArrayList<Notepad>();

            notepads.add(new Notepad(8, 3, 2026, "Концерт Flo Rida"));

            Notepad notepad = new Notepad(2, 3, 2026, "Сходить на флюорографию") {
                boolean importance = true;

                @Override
                public void setNotes(String string) {
                    if (importance) {
                        super.setNotes("Напоминание!" + string);
                    } else {
                        super.setNotes(string);
                    }
                }
            };
            notepad.setNotes("Пары начинаются с 9.00");
            notepads.add(notepad);

            notepads.add(new Notepad(20, 3, 2026, "Купить подарок на день рождения."));

            char choice = '0';

            while (choice == '0') {
                System.out.print("Введите 1, если желаете добавить запись на имеющуюся дату"
                        + "\nВведите 2, если желаете вывести все записи на определенную дату"
                        + "\nВведите 3, если желаете вывести все записи"
                        + "\nВведите 4, если желаете выйти"
                        + "\nВводите здесь: ");

                char choice1 = scanner.next().charAt(0);

                switch (choice1) {
                    case '1':
                        System.out.print("Введите дату, на которую вы желаете добавить запись.\n");
                        int[] array = Input(scanner);

                        boolean found = false;
                        for (Notepad notepad1 : notepads) {
                            if (notepad1.getDay() == array[0] &&
                                    notepad1.getMonth() == array[1] &&
                                    notepad1.getYear() == array[2]) {
                                notepad1.addNotes(scanner);
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Дата не найдена в записях!");
                        }
                        break;

                    case '2': {
                        System.out.print("Введите дату, записи на которую вы желаете вывести: \n");
                        array = Input(scanner);

                        found = false;
                        for (Notepad notepad1 : notepads) {
                            if (notepad1.getDay() == array[0] &&
                                    notepad1.getMonth() == array[1] &&
                                    notepad1.getYear() == array[2]) {
                                notepad1.ShowNotes();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Дата не найдена в записях!");
                        }
                        break;
                    }

                    case '3':
                        for (Notepad notepad1 : notepads) {
                            notepad1.Show();
                        }
                        break;

                    case '4':
                        choice = '1';
                        break;

                    default:
                        throw new IllegalArgumentException("\nОшибка! Вы ввели неверное число!");
                }
            }

            scanner.close();

        } catch (MyException e) {
            System.out.println("Ошибка ввода даты: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Исключение! " + e.getMessage());
        }
    }

    public static int[] Input(Scanner scanner) throws MyException {
        int day = 0, month = 0, year = 0;

        try {
            System.out.print("Введите день: ");
            if (scanner.hasNextInt()) {
                day = scanner.nextInt();
                if (day < 1 || day > 31) {
                    throw new MyException("День должен быть от 1 до 31");
                }
            } else {
                throw new MyException("Неверный формат дня");
            }

            System.out.print("Введите месяц: ");
            if (scanner.hasNextInt()) {
                month = scanner.nextInt();
                if (month < 1 || month > 12) {
                    throw new MyException("Месяц должен быть от 1 до 12");
                }
                if (month == 2 && day > 29) {
                    throw new MyException("В феврале не может быть больше 29 дней");
                }
                if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                    throw new MyException("В этом месяце не может быть больше 30 дней");
                }
            } else {
                throw new MyException("Неверный формат месяца");
            }

            System.out.print("Введите год: ");
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                if (year < 0) {
                    throw new MyException("Год не может быть отрицательным");
                }
            } else {
                throw new MyException("Неверный формат года");
            }

        } catch (InputMismatchException e) {
            throw new MyException("Ошибка ввода. Введите целые числа.");
        }

        return new int[]{day, month, year};
    }
}