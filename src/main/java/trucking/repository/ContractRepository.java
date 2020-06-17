package trucking.repository;

import trucking.model.*;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContractRepository extends CrudRepository<Contract, Long> {
    List<Contract> findAllByManager(Manager manager);
    List<Contract> findAllByContractors(Contractor contractor);
    List<Contract> findAll();
}
