package interfaces;

public interface Manageable extends Editable, Printable, Payable {
    void signContract();
    void resume();
    void terminate();
    String getStatus();
}