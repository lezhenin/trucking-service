package trucking.web.datatransfer;

import org.springframework.hateoas.RepresentationModel;

public class OfferData extends RepresentationModel<OrderData> {

    private Long id;

    private Long driverId;
    private Long orderId;
    private Long clientId;

    private int price;

    public OfferData(Long id, Long driverId, Long orderId, Long clientId, int price, boolean accepted) {
        this.id = id;
        this.driverId = driverId;
        this.orderId = orderId;
        this.clientId = clientId;
        this.price = price;
        this.accepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    private boolean accepted;

}
