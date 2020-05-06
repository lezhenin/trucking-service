package core.model;

import core.repository.RepositoryItem;
import core.repository.RepositorySingleton;

import java.util.List;

public class Driver extends Contract.Participant {

    private Contacts contacts;
    private Vehicle vehicle;

    public Driver(Contacts contacts, Vehicle vehicle) {
        super(Contract.ParticipantRole.DRIVER);
        this.contacts = contacts;
        this.vehicle = vehicle;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Contract> getContracts() {
        return RepositorySingleton
                .getInstance()
                .getContractRepository()
                .getByDriver(this);
    }

}
