package trucking.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trucking.model.Client;
import trucking.model.Contract;
import trucking.model.Order;
import trucking.repository.*;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.DataObjectMapper;
import trucking.web.datatransfer.NewOrderData;
import trucking.web.datatransfer.OrderData;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;

    public ClientService(
            @Autowired OrderRepository orderRepository,
            @Autowired ClientRepository clientRepository,
            @Autowired ContractRepository contractRepository
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
    }

    public List<OrderData> getOrders(Long clientId) {
        Client client = clientRepository.findById(clientId).get();
        List<Order> orders = orderRepository.findAllByClient(client);
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    public OrderData createOrder(Long clientId, NewOrderData newOrderData) {
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
