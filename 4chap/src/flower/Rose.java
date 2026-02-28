package flower;

public class Rose extends Flower {
    public Rose(String name, double price, String color, int freshness, double stemLength) {
        super(name, price, color, freshness, stemLength);
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}