package trucking.web.controllers.json;

import org.springframework.beans.factory.annotation.Autowired;
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
import trucking.web.services.ClientService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@ResponseBody
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;
    private final UsernameIdMapper usernameIdMapper;

    public ClientController(
            @Autowired ClientService clientService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.clientService = clientService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @GetMapping("/orders")
    List<OrderData> getOrders(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return clientService.getOrders(id);
    }

    @PostMapping("/orders")
    OrderData createOrder(Principal principal, @RequestBody NewOrderData orderData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return clientService.createOrder(id, orderData);
    }

    @DeleteMapping("/orders/{orderId}")
    void removeOrder(Principal principal, @PathVariable Long orderId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        clientService.removeOrder(id, orderId);
    }

    @GetMapping("/contracts")
    List<ContractData> getContracts(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return clientService.getContracts(id);
    }

    @GetMapping("/contracts/{contractId}/approve")
    ContractData approveContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return clientService.approveContract(id, contractId);
    }

    @GetMapping("/contracts/{contractId}/refuse")
    ContractData refuseContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return clientService.refuseContract(id, contractId);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return clientService.completeContract(id, contractId);
    }
    
}
