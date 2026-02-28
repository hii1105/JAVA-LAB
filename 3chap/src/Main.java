//1 вариант
//Создать классы, спецификации которых приведены ниже. Определить конструкторы и методы setТип(), getТип(), toString().
// Определить дополнительно методы в классе, создающем массив объектов.
// Задать критерий выбора данных
//вывести эти данные на консоль. В каждом классе, обладающем информацией, должно быть объявлено несколько конструкторов.
//Student: id, Фамилия, Имя, Отчество, Дата рождения, Адрес, Телефон, Факультет, Курс, Группа.
//Создать массив объектов. Вывести:
//a) список студентов заданного факультета;
//b) списки студентов для каждого факультета и курса;
//c) список студентов, родившихся после заданного года;
//d) список учебной группы
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private int birthYear;
    private String address;
    private String phone;
    private String faculty;
    private int course;
    private String group;
    //конструкторы
    public Student() {
        this(0, "", "", "", 2000, "", "", "", 1, "");
    }

    public Student(int id, String lastName, String firstName, String patronymic) {
        this(id, lastName, firstName, patronymic, 2000, "", "", "", 1, "");
    }

    public Student(int id, String lastName, String firstName, String patronymic,
                   int birthYear, String address, String phone,
                   String faculty, int course, String group) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthYear = birthYear;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }
    //set
    public void setId(int id) { this.id = id; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    public void setAddress(String address) {this.address = address; }
    public void setPhone(String phone) {this.phone = phone; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public void setCourse(int course) { this.course = course; }
    public void setGroup(String group) { this.group = group; }
    //get
    public int getId() { return id; }
    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }
    public int getBirthYear() { return birthYear; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getFaculty() { return faculty; }
    public int getCourse() { return course; }
    public String getGroup() { return group; }

    @Override
    public String toString() {
        return String.format("id: %d , %s , %d г.р. , %s , %d курс , %s , тел:%s , адрес:%s",
                id, getFullName(), birthYear, faculty, course, group, phone, address);
    }
}

class StudentManager {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student s) { students.add(s); }

    private void printStudentFullInfo(Student s) {
        System.out.println("  ID: " + s.getId());
        System.out.println("  ФИО: " + s.getFullName());
        System.out.println("  Год рождения: " + s.getBirthYear());
        System.out.println("  Факультет: " + s.getFaculty());
        System.out.println("  Курс: " + s.getCourse());
        System.out.println("  Группа: " + s.getGroup());
        System.out.println("  Телефон: " + s.getPhone());
        System.out.println("  Адрес: " + s.getAddress());
    }

    // a) список студентов заданного факультета
    public void printByFaculty(String faculty) {
        System.out.println("\nСтуденты факультета: " + faculty);
        List<Student> filtered = students.stream()
                .filter(s -> s.getFaculty().equalsIgnoreCase(faculty)).toList();

        if (filtered.isEmpty()) {
            System.out.println("  Студенты не найдены");
            return;
        }
        filtered.forEach(s -> System.out.println(s));
    }

    // b) списки студентов для каждого факультета и курса
    public void printByFacultyAndCourse() {
        System.out.println("\nСтуденты по факультетам и курсам:");

        students.stream().map(Student::getFaculty).distinct().forEach(fac -> {
            System.out.println("\nФакультет: " + fac);

            for (int course = 1; course <= 4; course++) {
                int c = course;
                List<Student> filtered = students.stream()
                        .filter(s -> s.getFaculty().equals(fac) && s.getCourse() == c).toList();

                if (!filtered.isEmpty()) {
                    System.out.println("  Курс " + course + " (" + filtered.size() + " студентов):");
                    filtered.forEach(s -> System.out.println("    • " + s.getFullName() +
                            " (гр." + s.getGroup()));
                }
            }
        });
    }

    // c) список студентов, родившихся после заданного года
    public void printBornAfter(int year) {
        System.out.println("\nСтуденты, родившиеся после " + year + " года:");
        List<Student> filtered = students.stream()
                .filter(s -> s.getBirthYear() > year).toList();

        if (filtered.isEmpty()) {
            System.out.println("  Студенты не найдены");
            return;
        }
        filtered.forEach(s -> System.out.println(s));
    }

    // d) список учебной группы
    public void printByGroup(String group) {
        System.out.println("\nСтуденты группы " + group + ":");
        List<Student> filtered = students.stream()
                .filter(s -> s.getGroup().equalsIgnoreCase(group)).toList();

        if (filtered.isEmpty()) {
            System.out.println("  Студенты не найдены");
            return;
        }
        filtered.forEach(s -> System.out.println(s));
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        // Добавление студентов с полной информацией
        manager.addStudent(new Student(1, "Иванов", "Иван", "Иванович",
                2000, "ул. Ленина 1, кв. 10", "+7(123)456-78-90", "ФКНТ", 4, "Б752"));

        manager.addStudent(new Student(2, "Петров", "Петр", "Петрович",
                2001, "ул. Гагарина 5, кв. 25", "+7(234)567-89-01", "ФКНТ", 3, "Б763-2"));

        manager.addStudent(new Student(3, "Зайцева", "Анна", "Сергеевна",
                2002, "ул. Пушкина 10, кв. 42", "+7(345)678-90-12", "Экономика", 2, "Б544"));

        Student s4 = new Student(4, "Смирнова", "Дарья", "Александровна");
        s4.setBirthYear(2002);
        s4.setFaculty("Экономика");
        s4.setCourse(2);
        s4.setGroup("Б544");
        s4.setAddress("ул. Лермонтова 15, кв. 7");
        s4.setPhone("+7(456)789-01-23");
        manager.addStudent(s4);

        Student s5 = new Student();
        s5.setId(5);
        s5.setLastName("Козлова");
        s5.setFirstName("Мария");
        s5.setPatronymic("Алексеевна");
        s5.setBirthYear(2003);
        s5.setFaculty("ФКНТ");
        s5.setCourse(1);
        s5.setGroup("Б765-1");
        s5.setAddress("ул. Мира 20, кв. 15");
        s5.setPhone("+7(567)890-12-34");
        manager.addStudent(s5);

        // menu
        while (true) {
            System.out.println("a - список студентов факультета");
            System.out.println("b - списки по факультетам и курсам");
            System.out.println("c - студенты после года");
            System.out.println("d - список группы");
            System.out.println("0 - выход");
            System.out.print("Выберите пункт: ");

            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                break;
            }

            switch (choice) {
                case "a":
                    System.out.print("Введите название факультета: ");
                    String faculty = scanner.nextLine();
                    manager.printByFaculty(faculty);
                    break;

                case "b":
                    manager.printByFacultyAndCourse();
                    break;

                case "c":
                    System.out.print("Введите год: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    manager.printBornAfter(year);
                    break;

                case "d":
                    System.out.print("Введите номер группы: ");
                    String group = scanner.nextLine();
                    manager.printByGroup(group);
                    break;

                default:
                    System.out.println("Ошибка! Введите a, b, c, d или 0");
            }
        }

        scanner.close();
    }
}