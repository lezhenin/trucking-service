package trucking.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trucking.model.*;
import trucking.repository.ContractRepository;
import trucking.repository.DriverRepository;
import trucking.repository.ManagerRepository;
import trucking.repository.OrderRepository;
import trucking.web.datatransfer.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final OrderRepository orderRepository;
    private final ContractRepository contractRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;

    public ManagerService(
            @Autowired OrderRepository orderRepository,
            @Autowired ContractRepository contractRepository,
            @Autowired ManagerRepository managerRepository,
            @Autowired DriverRepository driverRepository
    ) {
        this.orderRepository = orderRepository;
        this.contractRepository = contractRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
    }

    public List<OrderData> getOrders(Long managerId) {
        Manager manager = managerRepository.findById(managerId).get();
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public List<DriverData> getDrivers(Long managerId) {
        Manager manager = managerRepository.findById(managerId).get();
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().map(DataObjectMapper::dataFromDriver).collect(Collectors.toList());
    }

    public List<ContractData> getContracts(Long managerId) {
        Manager manager = managerRepository.findById(managerId).get();
        List<Contract> contracts = contractRepository.findAllByManager(manager);
        return contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    public ContractData createContract(Long managerId, NewContractData newContractData) {
        Manager manager = managerRepository.findById(managerId).get();
        Order order = orderRepository.findById(newContractData.getOrderId()).get();
        Driver driver = driverRepository.findById(newContractData.getDriverId()).get();
        Contract contract = DataObjectMapper.contractFromData(newContractData, order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData completeContract(Long managerId, Long contractId) throws Exception {
        Manager manager = managerRepository.findById(managerId).get();
        Contract contract = contractRepository.findById(contractId).get();
        manager.completeContract(contract);
        orderRepository.save(contract.getOrder());
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }


}
