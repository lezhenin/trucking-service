package trucking.core.model;

import trucking.core.repository.RepositoryItem;

import javax.persistence.*;

@Entity
@Table(name="ClientOrder")
public class Order extends RepositoryItem {

    public enum State {
        PUBLISHED,
        PROCESSED,
        COMPLETED
    }

    @ManyToOne private Client client;

    private int cargoWeight;
    private int cargoHeight;
    private int cargoWidth;
    private int cargoLength;

    private String shippingAddress;
    private String loadingAddress;

    // todo appropriate type
    private String deadline;

    private State orderState;

    public Order(
            Client client, int cargoWeight, int cargoHeight, int cargoWidth, int cargoLength,
            String shippingAddress, String loadingAddress, String deadline
    ) {
        this.client = client;
        this.cargoWeight = cargoWeight;
        this.cargoHeight = cargoHeight;
        this.cargoWidth = cargoWidth;
        this.cargoLength = cargoLength;
        this.shippingAddress = shippingAddress;
        this.loadingAddress = loadingAddress;
        this.deadline = deadline;
        this.orderState = State.PUBLISHED;
    }

    protected Order() { }

    public Client getClient() {
        return client;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public int getCargoHeight() {
        return cargoHeight;
    }

    public int getCargoWidth() {
        return cargoWidth;
    }

    public int getCargoLength() {
        return cargoLength;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getLoadingAddress() {
        return loadingAddress;
    }

    public String getDeadline() {
        return deadline;
    }

    public State getState() {
        return orderState;
    }

    public void setState(State orderState) {
        this.orderState = orderState;
    }
}
