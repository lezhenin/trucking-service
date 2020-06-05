package trucking.repository;

import trucking.model.Client;
import trucking.model.Contract;
import trucking.model.Driver;
import trucking.model.Manager;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContractRepository extends CrudRepository<Contract, Long> {
    List<Contract> findAllByManager(Manager manager);
    List<Contract> findAllByClient(Client client);
    List<Contract> findAllByDriver(Driver driver);
}
