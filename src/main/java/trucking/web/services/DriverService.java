package trucking.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trucking.model.*;
import trucking.repository.ContractRepository;
import trucking.repository.DriverRepository;
import trucking.repository.OfferRepository;
import trucking.repository.OrderRepository;
import trucking.web.datatransfer.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;
    private final ContractRepository contractRepository;
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;

    public DriverService(
            @Autowired DriverRepository driverRepository,
            @Autowired ContractRepository contractRepository,
            @Autowired OrderRepository orderRepository,
            @Autowired OfferRepository offerRepository
    ) {
        this.driverRepository = driverRepository;
        this.contractRepository = contractRepository;
        this.orderRepository = orderRepository;
        this.offerRepository = offerRepository;
    }

    public List<OrderData> getOrders(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        List<Contract> contracts = contractRepository.findAllByContractors(driver);
        List<Order> orders = contracts.stream().map(Contract::getOrder).collect(Collectors.toList());
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public List<OfferData> getOffers(Long driverId, Long orderId)  {
        Driver driver = driverRepository.findById(driverId).get();
        Order order = orderRepository.findById(orderId).get();
        List<Offer> offers = offerRepository.findAllByOrder(order);
        return offers.stream().map(DataObjectMapper::dataFromOffer).collect(Collectors.toList());
    }

    public OfferData createOffer(Long driverId, NewOfferData newOfferData) throws Exception {
        Driver driver = driverRepository.findById(driverId).get();
        Order order = orderRepository.findById(newOfferData.getOrderId()).get();
        Offer offer = DataObjectMapper.offerFromData(newOfferData, order, driver);
        driver.createOffer(offer);
        offerRepository.save(offer);
        return DataObjectMapper.dataFromOffer(offer);
    }

    public List<ContractData> getContracts(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        List<Contract> contracts = contractRepository.findAllByContractors(driver);
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
