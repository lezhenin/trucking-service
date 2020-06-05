package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import trucking.core.model.Contract;
import trucking.core.model.Driver;
import trucking.core.model.Manager;
import trucking.core.model.Order;
import trucking.core.repository.ContractRepository;
import trucking.core.repository.DriverRepository;
import trucking.core.repository.ManagerRepository;
import trucking.core.repository.OrderRepository;
import trucking.web.UsernameIdMapper;
import trucking.web.data.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
public class MangerWebController {

    private final OrderRepository orderRepository;
    private final ManagerRepository managerRepository;
    private final ContractRepository contractRepository;
    private final DriverRepository driverRepository;

    private final UsernameIdMapper usernameIdMapper;

    public MangerWebController(OrderRepository orderRepository, ManagerRepository managerRepository, ContractRepository contractRepository,  DriverRepository driverRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.managerRepository = managerRepository;
        this.contractRepository = contractRepository;
        this.driverRepository = driverRepository;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {

        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();

        List<Order> orders = manager.getOrders();
        List<OrderData> orderDataList = orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());

        model.addAttribute("orderDataList", orderDataList);
        return "/manager/orders";
    }

    @RequestMapping({"/contracts"})
    public String contracts(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();

        List<Contract> orders = manager.getContracts();
        List<ContractData> contractDataList = orders.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());

        NewContractData newContractData = new NewContractData();

        model.addAttribute("contractDataList", contractDataList);
        model.addAttribute("newContractData", newContractData);
        return "/manager/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"create"})
    public String CreateContract(Principal principal, NewContractData newContractData, BindingResult result) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();
        Order order = orderRepository.findById(newContractData.getOrderId()).get();
        Driver driver = driverRepository.findById(newContractData.getDriverId()).get();
        Contract contract = DataObjectMapper.contractFromData(newContractData, order, driver, manager);
        manager.createContract(contract);
        return "redirect:/manager/contracts";
    }


}
