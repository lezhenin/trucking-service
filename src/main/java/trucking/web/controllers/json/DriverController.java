package trucking.web.controllers.json;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import trucking.model.Client;
import trucking.model.Contract;
import trucking.model.Order;
import trucking.repository.ClientRepository;
import trucking.repository.ContractRepository;
import trucking.repository.OrderRepository;
import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.DataObjectMapper;
import trucking.web.datatransfer.NewOrderData;
import trucking.web.datatransfer.OrderData;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@ResponseBody
@RequestMapping("/api/driver")
public class DriverController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;

    private final UsernameIdMapper usernameIdMapper;

    public DriverController(OrderRepository orderRepository, ClientRepository clientRepository, ContractRepository contractRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
        this.usernameIdMapper = usernameIdMapper;
    }


    @GetMapping("/orders")
    List<OrderData> getOrders(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        List<Order> orders = client.getOrders();
        System.out.println(orders.size());
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }


    @PostMapping("/orders")
    OrderData createOrder(Principal principal, @RequestBody NewOrderData orderData) {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Order order = DataObjectMapper.orderFromData(orderData, client);
        orderRepository.save(order);
        client.createOrder(order);
        return DataObjectMapper.dataFromOrder(order);
    }

    @DeleteMapping("/orders/{orderId}")
    void removeOrder(Principal principal, @PathVariable Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Order order = orderRepository.findById(orderId).get();
        client.removeOrder(order);
    }


    @GetMapping("/contracts")
    List<ContractData> getContracts(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        return client.getContracts().stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    @GetMapping("/contracts/{contractId}/approve")
    ContractData approveContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractId).get();
        client.approveContract(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    @GetMapping("/contracts/{contractId}/refuse")
    ContractData refuseContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractId).get();
        client.refuseContract(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Client client = clientRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractId).get();
        client.completeContract(contract);
        return DataObjectMapper.dataFromContract(contract);
    }
    
}
