package trucking.web.datatransfer;

public class NewContractData {

    private Long orderId;
    private Long driverId;

    private int payment;

    public NewContractData() { }

    public NewContractData(Long orderId, Long clientId, Long driverId, int payment) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.payment = payment;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public int getPayment() {
        return payment;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

}
