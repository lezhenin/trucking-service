package trucking.model;
import javax.persistence.*;

@javax.persistence.Entity
public class Offer extends Entity {

    @ManyToOne private Order order;
    @ManyToOne private Driver driver;

    private int price;
    private boolean accepted;

    public Order getOrder() {
        return order;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
