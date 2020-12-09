package trucking.web.datatransfer;

public class NewOfferData {

    private int price;
    private Long orderId;

    public NewOfferData(int price, Long orderId) {
        this.price = price;
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
