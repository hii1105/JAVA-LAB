package models;

public class Magazine extends Publication {
    private int issueNumber;// номер выпуска
    private int volume;// том
    private String periodicity;// периодичность (ежемесячный, еженедельный)
    private String coverImage;// изображение на обложке

    public Magazine(String title, String author, int pages, double price,
                    int issueNumber, int volume, String periodicity) {
        super(title, author, pages, price);
        this.issueNumber = issueNumber;
        this.volume = volume;
        this.periodicity = periodicity;
        this.coverImage = "стандартная";
    }

    public void setCoverImage(String image) {
        this.coverImage = image;
        System.out.println("Обложка журнала обновлена: " + image);
    }

    public void addArticle(String articleTitle, String author) {
        System.out.println("Статья '" + articleTitle + "' добавлена в журнал");
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
        System.out.println("Номер выпуска изменен на: " + issueNumber);
    }

    @Override
    public void showSpecialInfo() {
        System.out.println("Выпуск №: " + issueNumber);
        System.out.println("Том: " + volume);
        System.out.println("Периодичность: " + periodicity);
        System.out.println("Обложка: " + coverImage);
    }

    @Override
    public void close() {

    }
}