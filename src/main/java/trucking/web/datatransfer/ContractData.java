package trucking.web.datatransfer;

import trucking.model.*;


public class ContractData {

    private Long id;

    private Long orderId;
    private Long clientId;
    private Long driverId;
    private Long managerId;

    private Contract.Status clientStatus;
    private Contract.Status driverStatus;

    private int payment;

    public ContractData() { }

    public ContractData(Long id, Long orderId, Long clientId, Long driverId, Long managerId, Contract.Status clientStatus, Contract.Status driverStatus, int payment) {
        this.id = id;
        this.orderId = orderId;
        this.clientId = clientId;
        this.driverId = driverId;
        this.managerId = managerId;
        this.clientStatus = clientStatus;
        this.driverStatus = driverStatus;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public Contract.Status getClientStatus() {
        return clientStatus;
    }

    public Contract.Status getDriverStatus() {
        return driverStatus;
    }

    public int getPayment() {
        return payment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public void setClientStatus(Contract.Status clientStatus) {
        this.clientStatus = clientStatus;
    }

    public void setDriverStatus(Contract.Status driverStatus) {
        this.driverStatus = driverStatus;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
