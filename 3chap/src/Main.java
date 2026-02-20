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
        this.id = 0;
        this.lastName = "";
        this.firstName = "";
        this.patronymic = "";
        this.birthYear = 2000;
        this.address = "";
        this.phone = "";
        this.faculty = "";
        this.course = 1;
        this.group = "";
    }

    public Student(int id, String lastName, String firstName, String patronymic) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthYear = 2000;
        this.address = "";
        this.phone = "";
        this.faculty = "";
        this.course = 1;
        this.group = "";
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
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public void setCourse(int course) { this.course = course; }
    public void setGroup(String group) { this.group = group; }
    //get
    public int getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getPatronymic() { return patronymic; }
    public int getBirthYear() { return birthYear; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getFaculty() { return faculty; }
    public int getCourse() { return course; }
    public String getGroup() { return group; }
    //toString
    public String toString() {
        return id + " " + lastName + " " + firstName + " " + patronymic +
                " " + birthYear + " " + faculty + " " + course + " " + group;
    }
}

class StudentManager {
    private Student[] students;
    private int count;

    public StudentManager(int size) {
        students = new Student[size];
        count = 0;
    }

    public void addStudent(Student s) {
        if (count < students.length) {
            students[count] = s;
            count++;
        }
    }

    // a) список студентов заданного факультета
    public void printByFaculty(String faculty) {
        System.out.println("\nСтуденты факультета: " + faculty);
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getFaculty().equalsIgnoreCase(faculty)) {
                System.out.println("  " + students[i].getLastName() + " " +
                        students[i].getFirstName() + " (" + students[i].getGroup() + ")");
                found = true;
            }
        }
        if (!found) {
            System.out.println("  Студенты не найдены");
        }
    }

    // b) списки студентов для каждого факультета и курса
    public void printByFacultyAndCourse() {
        System.out.println("\nСтуденты по факультетам и курсам: ");

        String[] faculties = new String[count];
        int facCount = 0;
        for (int i = 0; i < count; i++) {
            String f = students[i].getFaculty();
            boolean exists = false;
            for (int j = 0; j < facCount; j++) {
                if (faculties[j].equals(f)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                faculties[facCount] = f;
                facCount++;
            }
        }

        for (int i = 0; i < facCount; i++) {
            System.out.println("Факультет " + faculties[i] + ":");

            for (int course = 1; course <= 4; course++) {
                boolean hasStudents = false;
                System.out.print("Курс " + course + ": ");
                for (int j = 0; j < count; j++) {
                    if (students[j].getFaculty().equals(faculties[i]) &&
                            students[j].getCourse() == course) {
                        System.out.print(students[j].getLastName() + " ");
                        hasStudents = true;
                    }
                }
                if (!hasStudents) {
                    System.out.print("нет студентов");
                }
                System.out.println();
            }
        }
    }

    // c) список студентов, родившихся после заданного года
    public void printBornAfter(int year) {
        System.out.println("\nСтуденты, родившиеся после " + year + " года:");
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getBirthYear() > year) {
                System.out.println("  " + students[i].getLastName() + " " +
                        students[i].getFirstName() + " (" +
                        students[i].getBirthYear() + " г.)");
                found = true;
            }
        }
        if (!found) {
            System.out.println("  Студенты не найдены");
        }
    }

    // d) список учебной группы
    public void printByGroup(String group) {
        System.out.println("\nСтуденты группы " + group + ":");
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getGroup().equalsIgnoreCase(group)) {
                System.out.println("  " + students[i].getLastName() + " " +
                        students[i].getFirstName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("  Студенты не найдены");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StudentManager manager = new StudentManager(5);

        manager.addStudent(new Student(1, "Иванов", "Иван", "Иванович",
                2000, "ул. Ленина 1", "123-45-67",
                "ФКНТ", 4, "Б752"));

        manager.addStudent(new Student(2, "Петров", "Петр", "Петрович",
                2001, "ул. Ленина 2", "234-56-78",
                "ФКНТ", 3, "Б763-2"));

        manager.addStudent(new Student(3, "Зайцева", "Анна", "Сергеевна",
                2002, "ул. Ленина 3", "345-67-89",
                "Экономика", 2, "Б544"));

        Student s4 = new Student(4, "Смирнова", "Дарья", "Александровна");
        s4.setBirthYear(2002);
        s4.setFaculty("Экономика");
        s4.setCourse(2);
        s4.setGroup("Б544");
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