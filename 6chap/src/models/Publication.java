package models;

import interfaces.Manageable;
import java.util.Date;

public abstract class Publication implements Manageable {
    protected String title;
    protected String author;
    protected int pages;
    protected double price;
    protected String status;// статус (проект, в работе, готов, закрыт)
    protected boolean isPaidStatus;
    protected boolean isPrintedStatus;
    protected Date contractDate;
    protected String content;// содержание

    public Publication(String title, String author, int pages, double price) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.status = "проект";
        this.isPaidStatus = false;
        this.isPrintedStatus = false;
        this.contractDate = null;
        this.content = "";
    }

    @Override
    public void signContract() {
        this.contractDate = new Date();
        this.status = "договор подписан";
        System.out.println("Договор на издание '" + title + "' подписан " + contractDate);
    }

    @Override
    public void open() {
        if (status.equals("закрыто")) {
            System.out.println("Нельзя открыть закрытое издание");
            return;
        }
        this.status = "открыто";
        System.out.println("Издание '" + title + "' открыто для редактирования");
    }

    @Override
    public void edit(String content) {
        if (!status.equals("открыто") && !status.equals("в работе")) {
            System.out.println("Сначала откройте издание для редактирования");
            return;
        }
        this.content = content;
        this.status = "в работе";
        System.out.println("Издание '" + title + "' отредактировано");
    }

    @Override
    public void layout() {
        if (!status.equals("в работе") && !status.equals("открыто")) {
            System.out.println("Сначала отредактируйте издание");
            return;
        }
        this.status = "сверстано";
        System.out.println("Издание '" + title + "' сверстано");
    }

    @Override
    public void sendToPrint() {
        if (!status.equals("сверстано")) {
            System.out.println("Сначала завершите верстку");
            return;
        }
        if (!isPaidStatus) {
            System.out.println("Издание не оплачено");
            return;
        }
        this.isPrintedStatus = true;
        this.status = "отправлено в печать";
        System.out.println("Издание '" + title + "' отправлено в печать");
    }

    @Override
    public void cancelPrint() {
        if (isPrintedStatus) {
            System.out.println("Издание уже напечатано, отменить нельзя");
            return;
        }
        this.status = "отказ от печати";
        System.out.println("Печать издания '" + title + "' отменена");
    }

    @Override
    public boolean isPrinted() {
        return isPrintedStatus;
    }

    @Override
    public void pay(double amount) {
        if (amount >= price) {
            this.isPaidStatus = true;
            System.out.println("Издание '" + title + "' оплачено. Сумма: " + amount);
        } else {
            System.out.println("Недостаточная сумма для оплаты. Нужно: " + price);
        }
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public boolean isPaid() {
        return isPaidStatus;
    }

    @Override
    public void resume() {
        if (status.equals("закрыто")) {
            this.status = "проект";
            System.out.println("Издание '" + title + "' возобновлено");
        } else {
            System.out.println("Издание не было закрыто");
        }
    }

    @Override
    public void terminate() {
        this.status = "закрыто";
        System.out.println("Издание '" + title + "' закрыто");
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void printInfo() {
        System.out.println("Название: " + title);
        System.out.println("Автор: " + author);
        System.out.println("Страниц: " + pages);
        System.out.println("Цена: " + price);
        System.out.println("Статус: " + status);
        System.out.println("Оплачено: " + (isPaidStatus ? "да" : "нет"));
        System.out.println("Напечатано: " + (isPrintedStatus ? "да" : "нет"));
        if (contractDate != null) {
            System.out.println("Договор: " + contractDate);
        }
    }

    public abstract void showSpecialInfo();
}