package core.repository.list;

import core.model.Client;
import core.model.Contract;
import core.model.Driver;
import core.model.Manager;
import core.repository.ContractRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoRepositoryBean
public class ContractListRepository extends CrudListRepository<Contract> implements ContractRepository {

    public List<Contract> findAllByManager(Manager manager) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getManager().getId(), manager.getId()))
                .collect(Collectors.toList());
    }

    public List<Contract> findAllByClient(Client client) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getOrder().getClient().getId(), client.getId()))
                .collect(Collectors.toList());
    }

    public List<Contract> findAllByDriver(Driver driver) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getDriver().getId(), driver.getId()))
                .collect(Collectors.toList());
    }

}
