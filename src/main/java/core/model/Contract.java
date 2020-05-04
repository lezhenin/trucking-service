package core.model;

import core.repository.RepositoryItem;

import java.util.EnumSet;

public class Contract extends RepositoryItem {

    enum ContractState {
        APPROVED_BY_DRIVER,
        APPROVED_BY_CLIENT,
        REFUSED_BY_DRIVER,
        REFUSED_BY_CLIENT,
    }

    private Order order;
    private Driver driver;
    private Manager manager;

    private int payment;

    private EnumSet<ContractState> state;

    public Contract(Order order, Driver driver, Manager manager, int payment) {
        this.order = order;
        this.driver = driver;
        this.manager = manager;
        this.payment = payment;
        this.state = EnumSet.noneOf(ContractState.class);
    }

    public Order getOrder() {
        return order;
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

    public EnumSet<ContractState> getState() {
        return state;
    }
}
