//Вариант А, вариант 1
// Разработать проект управления процессами на основе создания и реализации интерфейсов для следующих предметных областей:
//1. Полиграфические издания. Возможности: оформить договор; открыть/редактировать/верстать издание;
// отправить на печать; отказаться от издания; оплатить издание; возобновить\закрыть издание.
// Добавить специализированные методы для Книги, Журнала, Учебного пособия

import models.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMainMenu();

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ошибка ввода!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    demoBook();
                    break;
                case 2:
                    demoMagazine();
                    break;
                case 3:
                    demoTextbook();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор!");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("1. Демонстрация работы с книгой");
        System.out.println("2. Демонстрация работы с журналом");
        System.out.println("3. Демонстрация работы с учебным пособием");
        System.out.println("4. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void demoBook() {
        System.out.println("\nДемонстрация работы с книгой:");
        Book book = new Book(
                "Война и мир",
                "Лев Толстой",
                1200,
                1500.0,
                "Роман",
                "",
                "твердая"
        );

        book.printInfo();

        //демонстрация всех возможностей
        System.out.println("\nПроцесс работы с книгой:");

        //оформить договор
        book.signContract();

        //открыть и редактировать
        book.open();
        book.edit("Текст книги...");

        //верстать
        book.layout();

        //специализированные методы для книги
        book.assignISBN("978-5-699-12345-6");
        book.setCoverType("твердая");
        book.addToSeries("Русская классика");

        //оплатить
        book.pay(1500.0);

        //отправить на печать
        book.sendToPrint();

        //показать специальную информацию
        book.showSpecialInfo();

        book.printInfo();
    }

    private static void demoMagazine() {
        System.out.println("\nДемонстрация работы с журналом:");
        Magazine magazine = new Magazine(
                "Vogue",
                "Condé Nast",
                250,
                850.0,
                156,
                12,
                "ежемесячный"
        );
        magazine.printInfo();

        System.out.println("\nПроцесс работы с журналом:");

        magazine.signContract();

        magazine.open();
        magazine.edit("Содержание журнала...");

        magazine.addArticle("Интервью с Ириной Шейк", "Анна Михайлова");
        magazine.addArticle("Тренды сезона", "Екатерина Дмитриева");

        magazine.layout();

        magazine.setCoverImage("обложка с Натальей Водяновой");
        magazine.setIssueNumber(43);

        magazine.pay(850.0);
        magazine.sendToPrint();

        magazine.showSpecialInfo();

        magazine.printInfo();
    }

    private static void demoTextbook() {
        System.out.println("\nДемонстрация работы с учебным пособием:");
        Textbook textbook = new Textbook(
                "Высшая математика",
                "Иванов И.И.",
                300,
                800.0,
                "Математика",
                "1 курс",
                "МГУ"
        );

        textbook.printInfo();

        System.out.println("\nПроцесс работы с учебным пособием:");

        textbook.signContract();

        textbook.open();
        textbook.edit("Учебный материал...");

        textbook.addExercises(50);

        textbook.layout();

        textbook.setGrade("2 курс");
        textbook.approveByMinistry();

        textbook.pay(800.0);

        textbook.sendToPrint();

        textbook.showSpecialInfo();

        textbook.printInfo();
    }
}