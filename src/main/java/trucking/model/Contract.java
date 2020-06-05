package trucking.model;

import trucking.repository.RepositoryItem;

import javax.persistence.*;

@Entity
public class Contract extends RepositoryItem {

    public enum Status {
        NONE,
        APPROVED,
        REFUSED,
        COMPLETED
    }

    @OneToOne private Order order;
    @OneToOne private Client client;
    @OneToOne private Driver driver;
    @OneToOne private Manager manager;

    private Status clientStatus;
    private Status driverStatus;

    private int payment;

    public Contract(Order order, Driver driver, Manager manager, int payment) {
        this.order = order;
        this.client = order.getClient();
        this.driver = driver;
        this.manager = manager;
        this.payment = payment;
        this.clientStatus = Status.NONE;
        this.driverStatus = Status.NONE;
    }

    protected Contract() { }

    public Order getOrder() {
        return order;
    }

    public Client getClient() {
        return client;
    }

    public Driver getDriver() {
        return driver;
    }

    public Manager getManager() {
        return manager;
    }

    public int getPayment() {
        return payment;
    }

    public Status getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(Status clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Status getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(Status driverStatus) {
        this.driverStatus = driverStatus;
    }

    public boolean isCompleted() {
        return this.clientStatus == Status.COMPLETED &&
                this.driverStatus == Status.COMPLETED;
    }


}


