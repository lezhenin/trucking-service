package trucking.core.repository;

import trucking.core.model.Client;
import trucking.core.model.Contract;
import trucking.core.model.Driver;
import trucking.core.model.Manager;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContractRepository extends CrudRepository<Contract, Long> {
    List<Contract> findAllByManager(Manager manager);
    List<Contract> findAllByClient(Client client);
    List<Contract> findAllByDriver(Driver driver);
}
