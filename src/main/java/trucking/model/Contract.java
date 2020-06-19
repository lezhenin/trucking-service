package trucking.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@javax.persistence.Entity
public  class Contract extends Entity {

    public enum Role {
        CLIENT, DRIVER
    }

    public enum Status {
        NONE,
        APPROVED,
        REFUSED,
        COMPLETED
    }

    @OneToOne private Order order;
    @ManyToOne private Manager manager;
    @ManyToMany(fetch = FetchType.EAGER) @MapKey(name = "role")
    private Map<Role, Contractor> contractors;
    @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(name = "Contract_ContractorStatus")
    private Map<Role, Status> contractorsStatus;

    private int payment;

    public Contract(Order order, Driver driver, Manager manager, int payment) {
        this.order = order;
        this.manager = manager;
        this.payment = payment;
        this.contractors = new HashMap<>();
        this.contractors.put(Role.CLIENT, order.getClient());
        this.contractors.put(Role.DRIVER, driver);
        this.contractorsStatus = new HashMap<>();
        this.contractorsStatus.put(Role.CLIENT, Status.NONE);
        this.contractorsStatus.put(Role.DRIVER, Status.NONE);
    }

    protected Contract() { }

    public Order getOrder() {
        return order;
    }

    public Client getClient() {
        return (Client) contractors.get(Role.CLIENT);
    }

    public Driver getDriver() {
        return (Driver) contractors.get(Role.DRIVER);
    }

    public Manager getManager() {
        return manager;
    }

    public int getPayment() {
        return payment;
    }

    public Status getClientStatus() {
        return getContractorStatus(Role.CLIENT);
    }

    public Status getDriverStatus() {
        return getContractorStatus(Role.DRIVER);
    }

    public boolean isCompleted() {
        return this.contractorsStatus.values()
                .stream().allMatch(s -> s == Status.COMPLETED);
    }

    public boolean isApproved() {
        return this.contractorsStatus.values()
                .stream().allMatch(s -> s == Status.APPROVED || s == Contract.Status.COMPLETED);
    }

    public boolean isRefused() {
        return this.contractorsStatus.values()
                .stream().anyMatch(s -> s == Status.REFUSED);
    }

    public Map<Role, Contractor> getContractors() {
        return Collections.unmodifiableMap(contractors);
    }

    Map<Role, Status> getContractorsStatus() {
        return Collections.unmodifiableMap(contractorsStatus);
    }

    Status getContractorStatus(Role role) {
        return contractorsStatus.get(role);
    }

    void setContractorStatus(Role role, Status status) {
        contractorsStatus.put(role, status);
    }


}


