package interfaces;

public interface Payable {
    void pay(double amount);
    double getPrice();
    boolean isPaid();
}