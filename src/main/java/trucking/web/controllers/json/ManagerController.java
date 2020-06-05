package trucking.web.controllers.json;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("/api/manager")
public class ManagerController {

    private final OrderRepository orderRepository;
    private final ContractRepository contractRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final UsernameIdMapper usernameIdMapper;

    public ManagerController(OrderRepository orderRepository, ContractRepository contractRepository, DriverRepository driverRepository, ManagerRepository managerRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.contractRepository = contractRepository;
        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
        this.usernameIdMapper = usernameIdMapper;
    }

    @GetMapping("/contracts")
    List<ContractData> getContracts(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();
        return manager.getContracts().stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    @PostMapping("/createContract")
    ContractData createOrder(Principal principal, @RequestBody NewContractData contractData) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();
        Order order = orderRepository.findById(contractData.getOrderId()).get();
        Driver driver = driverRepository.findById(contractData.getDriverId()).get();
        Contract contract = DataObjectMapper.contractFromData(contractData, order, driver, manager);
        contractRepository.save(contract);
        manager.createContract(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    @GetMapping("/orders")
    List<OrderData> getOrders(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();
        return manager.getOrders().stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    @GetMapping("/drivers")
    List<DriverData> getDrivers(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();
        return manager.getDrivers().stream().map(DataObjectMapper::dataFromDriver).collect(Collectors.toList());
    }

}
