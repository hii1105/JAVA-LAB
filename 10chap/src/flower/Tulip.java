package flower;
import java.io.Serializable;
public class Tulip extends Flower implements Serializable {
    private static final long serialVersionUID = 1L;
    public Tulip(String name, double price, String color, int freshness, double stemLength) {
        super(name, price, color, freshness, stemLength);
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}