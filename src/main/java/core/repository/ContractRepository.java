package core.repository;

import core.model.Client;
import core.model.Contract;
import core.model.Driver;
import core.model.Manager;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContractRepository
        extends ListRepository<Contract> {

    public List<Contract> getByManager(Manager manager) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getManager().getId(), manager.getId()))
                .collect(Collectors.toList());
    }

    public List<Contract> getByClient(Client client) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getOrder().getClient().getId(), client.getId()))
                .collect(Collectors.toList());
    }

    public List<Contract> getByDriver(Driver driver) {
        return itemList.stream().
                filter(contract -> Objects.equals(contract.getDriver().getId(), driver.getId()))
                .collect(Collectors.toList());
    }

}
