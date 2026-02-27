package models;

public class Textbook extends Publication {
    private String subject;           // предмет
    private String grade;              // класс/курс
    private boolean hasExercises;      // есть ли упражнения
    private String university;         // университет/школа

    public Textbook(String title, String author, int pages, double price,
                    String subject, String grade, String university) {
        super(title, author, pages, price);
        this.subject = subject;
        this.grade = grade;
        this.university = university;
        this.hasExercises = true;
    }

    public void addExercises(int count) {
        if (!hasExercises) {
            this.hasExercises = true;
        }
        System.out.println("Добавлено " + count + " упражнений");
    }

    public void setGrade(String grade) {
        this.grade = grade;
        System.out.println("Класс/курс изменен на: " + grade);
    }

    public void approveByMinistry() {
        System.out.println("Учебное пособие одобрено министерством образования");
    }

    @Override
    public void showSpecialInfo() {
        System.out.println("Предмет: " + subject);
        System.out.println("Класс/Курс: " + grade);
        System.out.println("Упражнения: " + (hasExercises ? "есть" : "нет"));
        System.out.println("Учреждение: " + university);
    }

    @Override
    public void close() {

    }
}