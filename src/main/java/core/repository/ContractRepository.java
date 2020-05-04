package core.repository;

import core.model.Client;
import core.model.Contract;
import core.model.Driver;
import core.model.Manager;

import java.util.List;

public interface ContractRepository {

    void add(Contract contract);
    void remove(Contract contract);
    List<Contract> getAll();
    List<Contract> getByManager(Manager manager);
    List<Contract> getByClient(Client client);
    List<Contract> getByDriver(Driver driver);

}
