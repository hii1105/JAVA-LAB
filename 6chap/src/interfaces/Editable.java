package interfaces;

public interface Editable {
    void open();
    void edit(String content);
    void layout();// верстать издание
    void close();
}