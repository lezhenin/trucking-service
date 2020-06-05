package trucking.web.data;


public class NewContractData {

    private Long orderId;
    private Long driverId;
    private Long managerId;

    private int payment;

    public NewContractData() { }

    public NewContractData(Long orderId, Long clientId, Long driverId, Long managerId, int payment) {
        this.orderId = orderId;
        this.driverId = driverId;
        this.managerId = managerId;
        this.payment = payment;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public int getPayment() {
        return payment;
    }
}
