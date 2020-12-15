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
        Client client = clientRepository.findById(clientId).get();
        List<Order> orders = orderRepository.findAllByClient(client);
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }


    public Optional<OrderData> getOrder(Long clientId, Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order = order.filter((o) -> Objects.equals(o.getClient().getId(), clientId));
        return order.map(DataObjectMapper::dataFromOrder);
    }

    public List<OfferData> getOffers(Long clientId, Long orderId)  {
        Client client = clientRepository.findById(clientId).get();

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            return Collections.emptyList();
        }

        Order order = optionalOrder.get();
        List<Offer> offers = offerRepository.findAllByOrder(order);
        return offers.stream().map(DataObjectMapper::dataFromOffer).collect(Collectors.toList());
    }

    public Optional<OfferData> getOffer(Long clientId, Long offerId)  {
        Optional<Offer> offer = offerRepository.findById(offerId);
        offer = offer.filter((o) -> Objects.equals(o.getOrder().getClient().getId(), clientId));
        return offer.map(DataObjectMapper::dataFromOffer);
    }

    public OfferData acceptOffer(Long clientId, Long offerId) throws Exception {
        Client client = clientRepository.findById(clientId).get();
        Offer offer = offerRepository.findById(offerId).get();
        client.acceptOffer(offer);
        offerRepository.save(offer);
        orderRepository.save(offer.getOrder());
        return DataObjectMapper.dataFromOffer(offer);
    }

    public OrderData createOrder(Long clientId, NewOrderData newOrderData) throws Exception {
        Client client = clientRepository.findById(clientId).get();
        Order order = DataObjectMapper.orderFromData(newOrderData, client);
        client.createOrder(order);
        orderRepository.save(order);
        return DataObjectMapper.dataFromOrder(order);
    }

    public void removeOrder(Long clientId, Long orderId) throws Exception {
        Client client = clientRepository.findById(clientId).get();
        Order order = orderRepository.findById(orderId).get();
        client.removeOrder(order);
        orderRepository.delete(order);
    }

    public List<ContractData> getContracts(Long clientId) {
        Client client = clientRepository.findById(clientId).get();
        List<Contract> contracts = contractRepository.findAllByContractors(client);
        return contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    public Optional<ContractData> getContract(Long clientId, Long contractId) {
        Optional<Contract> contract = contractRepository.findById(contractId);
        contract = contract.filter((o) -> Objects.equals(o.getClient().getId(), clientId));
        return contract.map(DataObjectMapper::dataFromContract);
    }

    public ContractData approveContract(Long clientId, Long contractId) throws Exception {
        Client client = clientRepository.findById(clientId).get();
        Contract contract = contractRepository.findById(contractId).get();
        client.approveContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData refuseContract(Long clientId, Long contractId) throws Exception {
        Client client = clientRepository.findById(clientId).get();
        Contract contract = contractRepository.findById(contractId).get();
        client.refuseContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    public ContractData completeContract(Long clientId, Long contractId) throws Exception {
        Client client = clientRepository.findById(clientId).get();
        Contract contract = contractRepository.findById(contractId).get();
        client.completeContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

}
