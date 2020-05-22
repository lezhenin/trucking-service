package core.repository;

import core.model.Client;
import core.model.Contract;
import core.model.Driver;
import core.model.Manager;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContractRepository extends CrudRepository<Contract, Long> {
    List<Contract> findAllByManager(Manager manager);
    List<Contract> findAllByClient(Client client);
    List<Contract> findAllByDriver(Driver driver);
}
