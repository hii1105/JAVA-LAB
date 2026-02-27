package models;

public class Book extends Publication {
    private String genre;// жанр
    private String isbn;// ISBN номер
    private String coverType;// тип обложки (твердая/мягкая)

    public Book(String title, String author, int pages, double price,
                String genre, String isbn, String coverType) {
        super(title, author, pages, price);
        this.genre = genre;
        this.isbn = isbn;
        this.coverType = coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
        System.out.println("Тип обложки изменен на: " + coverType);
    }

    public void assignISBN(String isbn) {
        this.isbn = isbn;
        System.out.println("ISBN присвоен: " + isbn);
    }

    public void addToSeries(String seriesName) {
        System.out.println("Книга добавлена в серию: " + seriesName);
    }

    @Override
    public void showSpecialInfo() {
        System.out.println("Жанр: " + genre);
        System.out.println("ISBN: " + (isbn.isEmpty() ? "не присвоен" : isbn));
        System.out.println("Обложка: " + coverType);
    }

    @Override
    public void close() {

    }
}