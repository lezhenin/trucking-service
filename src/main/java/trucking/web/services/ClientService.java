package trucking.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.datatransfer.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;
    private final OfferRepository offerRepository;

    public ClientService(
            @Autowired OrderRepository orderRepository,
            @Autowired ClientRepository clientRepository,
            @Autowired ContractRepository contractRepository,
            @Autowired OfferRepository offerRepository
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
        this.offerRepository = offerRepository;
    }

    public List<OrderData> getOrders(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        List<Order> orders = orderRepository.findAllByClient(client);
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public OrderData getOrder(Long clientId, Long orderId) throws NoSuchElementException {
        Optional<Order> order = orderRepository.findById(orderId);
        order = order.filter((o) -> Objects.equals(o.getClient().getId(), clientId));
        return order.map(DataObjectMapper::dataFromOrder).orElseThrow();
    }

    public List<OfferData> getOffers(Long clientId, Long orderId) throws NoSuchElementException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent() || !Objects.equals(optionalOrder.get().getClient().getId(), clientId)) {
            return Collections.emptyList();
        }
        Order order = optionalOrder.orElseThrow();
        List<Offer> offers = offerRepository.findAllByOrder(order);
        return offers.stream().map(DataObjectMapper::dataFromOffer).collect(Collectors.toList());
    }

    public OfferData getOffer(Long clientId, Long offerId) throws NoSuchElementException {
        Optional<Offer> offer = offerRepository.findById(offerId);
        offer = offer.filter((o) -> Objects.equals(o.getOrder().getClient().getId(), clientId));
        return offer.map(DataObjectMapper::dataFromOffer).orElseThrow();
    }

    public OfferData acceptOffer(Long clientId, Long offerId) throws NoSuchElementException, ModelException {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        client.acceptOffer(offer);
        offerRepository.save(offer);
        orderRepository.save(offer.getOrder());
        return DataObjectMapper.dataFromOffer(offer);
    }

    public OrderData createOrder(Long clientId, NewOrderData newOrderData) throws ModelException {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Order order = DataObjectMapper.orderFromData(newOrderData, client);
        client.createOrder(order);
        orderRepository.save(order);
        return DataObjectMapper.dataFromOrder(order);
    }

    public void removeOrder(Long clientId, Long orderId) throws NoSuchElementException, ModelException {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Order order = orderRepository.findById(orderId).orElseThrow();
        client.removeOrder(order);
        orderRepository.delete(order);
    }

    public List<ContractData> getContracts(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        List<Contract> contracts = contractRepository.findAllByContractors(client);
        return contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    public ContractData getContract(Long clientId, Long contractId) throws NoSuchElementException {
        Optional<Contract> contract = contractRepository.findById(contractId);
        contract = contract.filter((o) -> Objects.equals(o.getClient().getId(), clientId));
        return contract.map(DataObjectMapper::dataFromContract).orElseThrow();
    }

    public ContractData approveContract(Long clientId, Long contractId) throws NoSuchElementException, ModelException {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        client.approveContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData refuseContract(Long clientId, Long contractId) throws NoSuchElementException, ModelException {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        client.refuseContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData completeContract(Long clientId, Long contractId) throws NoSuchElementException, ModelException {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        client.completeContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

}
