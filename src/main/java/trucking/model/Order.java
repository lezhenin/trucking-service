package trucking.model;


import javax.persistence.*;

@javax.persistence.Entity
@Table(name="ClientOrder")
public class Order extends Entity {

    public enum State {
        PUBLISHED,
        PROCESSED,
        COMPLETED,
        REMOVED
    }

    @ManyToOne private Client client;
    @OneToOne(orphanRemoval = true) private Offer offer = null;

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

    public boolean hasAcceptedOffer() {
        return offer != null;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public State getState() {
        return orderState;
    }

    void setState(State orderState) {
        this.orderState = orderState;
    }
}
