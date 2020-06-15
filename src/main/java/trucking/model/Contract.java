package trucking.model;

import trucking.repository.RepositoryItem;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
public  class Contract extends RepositoryItem {

    public enum ParticipantRole {
        CLIENT, DRIVER
    }

    public enum Status {
        NONE,
        APPROVED,
        REFUSED,
        COMPLETED
    }

    @OneToOne private Order order;
    @ManyToOne private Client client;
    @ManyToOne private Driver driver;
    @ManyToOne private Manager manager;

    private Status clientStatus;
    private Status driverStatus;

    @Transient
    private Map<ParticipantRole, Status> participantStatus;
    @Transient
    private Map<ParticipantRole, ContractParticipant> participants;

    private int payment;

    public Contract(Order order, Driver driver, Manager manager, int payment) {
        this.order = order;
        this.client = order.getClient();
        this.driver = driver;
        this.manager = manager;
        this.payment = payment;
        this.clientStatus = Status.NONE;
        this.driverStatus = Status.NONE;
        this.initStatusMap();
    }

    protected Contract() { }

    @PostLoad
    private void initStatusMap(){
        this.participants = new HashMap<>();
        this.participants.put(ParticipantRole.CLIENT, client);
        this.participants.put(ParticipantRole.DRIVER, driver);
        this.participantStatus = new HashMap<>();
        this.participantStatus.put(ParticipantRole.CLIENT, clientStatus);
        this.participantStatus.put(ParticipantRole.DRIVER, driverStatus);
    }

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
        return this.participantStatus.values().stream().allMatch(s -> s == Status.COMPLETED);
    }

    Map<ParticipantRole, ContractParticipant> getAllParticipants() {
        return Collections.unmodifiableMap(participants);
    }

    Map<ParticipantRole, Status> getAllParticipantStatus() {
        return Collections.unmodifiableMap(participantStatus);
    }

    Status getParticipantStatus(ParticipantRole role) {
        return participantStatus.get(role);
    }

    void setParticipantStatus(ParticipantRole role, Status status) {
        participantStatus.put(role, status);
    }


}


