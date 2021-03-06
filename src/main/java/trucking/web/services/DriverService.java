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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class DriverService {

    public enum OrderView {
        ACCEPTED,
        AVAILABLE
    }

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
        return getAcceptedOrders(driverId);
    }

    public List<OrderData> getOrders(Long driverId, OrderView view) {
        if (view == null || view == OrderView.ACCEPTED) {
            return getAcceptedOrders(driverId);
        } else {
            return getAvailableOrders(driverId);
        }
    }

    public List<OrderData> getAcceptedOrders(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        List<Contract> contracts = contractRepository.findAllByContractors(driver);
        List<Order> orders = contracts.stream().map(Contract::getOrder).collect(Collectors.toList());
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public List<OrderData> getAvailableOrders(Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        Stream<Order> orders = orderRepository.findAll().stream().filter(o -> o.getOffer() == null);
        return orders.map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public Optional<OrderData> getOrder(Long driverId, Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(DataObjectMapper::dataFromOrder);
    }

    public List<OfferData> getOffers(Long driverId, Long orderId)  {
        Driver driver = driverRepository.findById(driverId).get();

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            return Collections.emptyList();
        }

        Order order = optionalOrder.get();
        List<Offer> offers = offerRepository.findAllByOrder(order);
        return offers.stream().map(DataObjectMapper::dataFromOffer).collect(Collectors.toList());
    }

    public Optional<OfferData> getOffer(Long driverId, Long offerId)  {
        Optional<Offer> offer = offerRepository.findById(offerId);
        offer = offer.filter((o) -> Objects.equals(o.getDriver().getId(), driverId));
        return offer.map(DataObjectMapper::dataFromOffer);
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

    public Optional<ContractData> getContract(Long driverId, Long contractId) {
        Optional<Contract> contract = contractRepository.findById(contractId);
        contract = contract.filter((o) -> Objects.equals(o.getDriver().getId(), driverId));
        return contract.map(DataObjectMapper::dataFromContract);
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
