package flower;

public class Lily extends Flower {
    public Lily(String name, double price, String color, int freshness, double stemLength) {
        super(name, price, color, freshness, stemLength);
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}