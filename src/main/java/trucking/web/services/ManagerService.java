package trucking.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.datatransfer.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManagerService {

    private final OrderRepository orderRepository;
    private final ContractRepository contractRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;
    private final OfferRepository offerRepository;

    public ManagerService(
            @Autowired OrderRepository orderRepository,
            @Autowired ContractRepository contractRepository,
            @Autowired ManagerRepository managerRepository,
            @Autowired DriverRepository driverRepository,
            @Autowired OfferRepository offerRepository
    ) {
        this.orderRepository = orderRepository;
        this.contractRepository = contractRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
        this.offerRepository = offerRepository;
    }

    public List<OrderData> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public Optional<OrderData> getOrder(Long orderId) {
        return orderRepository.findById(orderId).map(DataObjectMapper::dataFromOrder);
    }

    public List<OfferData> getOffers(Long orderId)  {
        Order order = orderRepository.findById(orderId).get();
        List<Offer> offers = offerRepository.findAllByOrder(order);
        return offers.stream().map(DataObjectMapper::dataFromOffer).collect(Collectors.toList());
    }

    public Optional<OfferData> getOffer(Long offerId)  {
        Optional<Offer> offer = offerRepository.findById(offerId);
        return offer.map(DataObjectMapper::dataFromOffer);
    }

    public List<DriverData> getDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().map(DataObjectMapper::dataFromDriver).collect(Collectors.toList());
    }

    public Optional<DriverData> getDriver(Long driverId) {
        Optional<Driver> driver = driverRepository.findById(driverId);
        return driver.map(DataObjectMapper::dataFromDriver);
    }

    public List<ContractData> getContracts(Long managerId) {
        Manager manager = managerRepository.findById(managerId).get();
        List<Contract> contracts = contractRepository.findAllByManager(manager);
        return contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    public Optional<ContractData> getContract(Long managerId, Long contractId) {
        Manager manager = managerRepository.findById(managerId).get();
        return contractRepository.findById(contractId)
                .filter(c -> Objects.equals(c.getManager().getId(), manager.getId()))
                .map(DataObjectMapper::dataFromContract);
    }

    public ContractData createContract(Long managerId, NewContractData newContractData) throws Exception {
        Manager manager = managerRepository.findById(managerId).get();
        Order order = orderRepository.findById(newContractData.getOrderId()).get();
        Driver driver = driverRepository.findById(newContractData.getDriverId()).get();
        Contract contract = DataObjectMapper.contractFromData(newContractData, order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData removeContract(Long managerId, Long contractId) throws Exception {
        Manager manager = managerRepository.findById(managerId).get();
        Contract contract = contractRepository.findById(contractId).get();
        manager.removeContract(contract);
        orderRepository.save(contract.getOrder());
        contractRepository.delete(contract);
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
