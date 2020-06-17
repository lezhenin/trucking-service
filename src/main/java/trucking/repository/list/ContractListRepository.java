package trucking.repository.list;

import trucking.model.*;
import trucking.repository.ClientRepository;
import trucking.repository.ContractRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContractListRepository extends CrudListRepository<Contract> implements ContractRepository {

    public List<Contract> findAllByManager(Manager manager) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getManager().getId(), manager.getId()))
                .collect(Collectors.toList());
    }

    public List<Contract> findAllByContractors(Contractor contractor) {
        return itemList.stream().
                filter(contract -> {
                    return contract.getContractors().values()
                            .stream().anyMatch(c -> Objects.equals(c.getId(), contract.getId()));
                }).collect(Collectors.toList());
    }

}
