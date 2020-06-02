package trucking.core.model;

import trucking.core.repository.RepositoryItem;
import trucking.core.repository.RepositorySingleton;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Driver extends RepositoryItem {

    @Embedded private Contacts contacts;

    @OneToOne private Vehicle vehicle;

    public Driver(Contacts contacts, Vehicle vehicle) {
        this.contacts = contacts;
        this.vehicle = vehicle;
    }

    protected Driver() { }

    public Contacts getContacts() {
        return contacts;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Contract> getContracts() {
        return RepositorySingleton.getInstance()
                .getContractRepository()
                .findAllByDriver(this);
    }

    public void approveContract(Contract contract) throws Exception {
        if (!contract.getDriver().getId().equals(this.getId())) {
            throw new Exception();
        }
        if (contract.getDriverStatus() != Contract.Status.NONE) {
            throw new Exception();
        }

        contract.setDriverStatus(Contract.Status.APPROVED);
        RepositorySingleton.getInstance()
                .getContractRepository()
                .save(contract);
    }

    public void refuseContract(Contract contract) throws Exception {
        if (!contract.getDriver().getId().equals(this.getId())) {
            throw new Exception();
        }
        if (contract.getDriverStatus() != Contract.Status.NONE) {
            throw new Exception();
        }

        contract.setDriverStatus(Contract.Status.REFUSED);
        RepositorySingleton.getInstance()
                .getContractRepository()
                .save(contract);
    }

    public void completeContract(Contract contract) throws Exception {
        if (!contract.getDriver().getId().equals(this.getId())) {
            throw new Exception();
        }
        if (contract.getDriverStatus() == Contract.Status.NONE ||
                contract.getDriverStatus() == Contract.Status.REFUSED) {
            throw new Exception();
        }
        if (contract.getClientStatus() == Contract.Status.NONE ||
                contract.getClientStatus() == Contract.Status.REFUSED) {
            throw new Exception();
        }
        if (contract.getDriverStatus() == Contract.Status.COMPLETED) {
            throw new Exception();
        }

        contract.setDriverStatus(Contract.Status.COMPLETED);
        RepositorySingleton.getInstance()
                .getContractRepository()
                .save(contract);
    }

}
