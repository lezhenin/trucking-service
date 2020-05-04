package core.model;

import core.repository.RepositoryItem;

public class Order extends RepositoryItem {

    enum OrderState {
        PUBLISHED,
        PROCESSED,
        DONE
    }

    private Client client;

    private int cargoWeight;
    private int cargoHeight;
    private int cargoWidth;
    private int cargoLength;

    private String shippingAddress;
    private String loadingAddress;

    // todo appropriate type
    private String deadline;

    private OrderState state;

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
        this.state = OrderState.PUBLISHED;
    }

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

    public OrderState getState() {
        return state;
    }
}
