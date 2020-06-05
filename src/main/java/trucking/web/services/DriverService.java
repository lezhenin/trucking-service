package trucking.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trucking.model.Contract;
import trucking.model.Driver;
import trucking.model.Order;
import trucking.repository.ContractRepository;
import trucking.repository.DriverRepository;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.DataObjectMapper;
import trucking.web.datatransfer.OrderData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final ContractRepository contractRepository;

    public DriverService(
            @Autowired DriverRepository driverRepository,
            @Autowired ContractRepository contractRepository
    ) {
        this.driverRepository = driverRepository;
        this.contractRepository = contractRepository;
    }

    public List<OrderData> getOrders(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        List<Contract> contracts = contractRepository.findAllByDriver(driver);
        List<Order> orders = contracts.stream().map(Contract::getOrder).collect(Collectors.toList());
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public List<ContractData> getContracts(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        List<Contract> contracts = contractRepository.findAllByDriver(driver);
        return contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    public ContractData approveContract(Long driverId, Long contractId) throws Exception {
        Driver driver = driverRepository.findById(driverId).get();
        Contract contract = contractRepository.findById(contractId).get();
        driver.approveContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData refuseContract(Long driverId, Long contractId) throws Exception {
        Driver driver = driverRepository.findById(driverId).get();
        Contract contract = contractRepository.findById(contractId).get();
        driver.refuseContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData completeContract(Long driverId, Long contractId) throws Exception {
        Driver driver = driverRepository.findById(driverId).get();
        Contract contract = contractRepository.findById(contractId).get();
        driver.completeContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

}
