package trucking.web.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import trucking.core.model.Order;

public class OrderData {

    @JsonProperty
    private Long id;

    @JsonProperty
    private int cargoWeight;
    @JsonProperty
    private int cargoHeight;
    @JsonProperty
    private int cargoWidth;
    @JsonProperty
    private int cargoLength;

    @JsonProperty
    private String shippingAddress;
    @JsonProperty
    private String loadingAddress;
    @JsonProperty
    private String deadline;

    @JsonProperty
    private Long clientId;

    @JsonProperty
    private Order.State orderState;

    public OrderData() { }

    public OrderData(
            Long id,
            int cargoWeight,
            int cargoHeight,
            int cargoWidth,
            int cargoLength,
            String shippingAddress,
            String loadingAddress,
            String deadline,
            Long clientId,
            Order.State orderState
    ) {
        this.id = id;
        this.cargoWeight = cargoWeight;
        this.cargoHeight = cargoHeight;
        this.cargoWidth = cargoWidth;
        this.cargoLength = cargoLength;
        this.shippingAddress = shippingAddress;
        this.loadingAddress = loadingAddress;
        this.deadline = deadline;
        this.clientId = clientId;
        this.orderState = orderState;
    }

    public Long getId() {
        return id;
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

    public Long getClientId() {
        return clientId;
    }

    public Order.State getOrderState() {
        return orderState;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public void setCargoHeight(int cargoHeight) {
        this.cargoHeight = cargoHeight;
    }

    public void setCargoWidth(int cargoWidth) {
        this.cargoWidth = cargoWidth;
    }

    public void setCargoLength(int cargoLength) {
        this.cargoLength = cargoLength;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setLoadingAddress(String loadingAddress) {
        this.loadingAddress = loadingAddress;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setOrderState(Order.State orderState) {
        this.orderState = orderState;
    }
}
