package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import trucking.model.Contract;
import trucking.model.Driver;
import trucking.model.Manager;
import trucking.model.Order;
import trucking.repository.ContractRepository;
import trucking.repository.DriverRepository;
import trucking.repository.ManagerRepository;
import trucking.repository.OrderRepository;
import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
public class MangerWebController {

    private final OrderRepository orderRepository;
    private final ContractRepository contractRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;

    private final UsernameIdMapper usernameIdMapper;

    public MangerWebController(OrderRepository orderRepository, ContractRepository contractRepository, ManagerRepository managerRepository, DriverRepository driverRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.contractRepository = contractRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {

        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();

        List<Order> orders = orderRepository.findAll();
        List<OrderData> orderDataList =
                orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());

        model.addAttribute("orderDataList", orderDataList);
        return "/manager/orders";
    }

    @RequestMapping({"/contracts"})
    public String contracts(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();

        List<Contract> contracts = contractRepository.findAllByManager(manager);
        List<ContractData> contractDataList =
                contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());

        NewContractData newContractData = new NewContractData();

        model.addAttribute("contractDataList", contractDataList);
        model.addAttribute("newContractData", newContractData);
        return "/manager/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"create"})
    @Transactional
    public String createContract(Principal principal, NewContractData newContractData, BindingResult result) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();
        Order order = orderRepository.findById(newContractData.getOrderId()).get();
        Driver driver = driverRepository.findById(newContractData.getDriverId()).get();
        Contract contract = DataObjectMapper.contractFromData(newContractData, order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);
        return "redirect:/manager/contracts";
    }

    @RequestMapping({"/drivers"})
    public String drivers(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        Manager manager = managerRepository.findById(id).get();

        List<Driver> drivers = driverRepository.findAll();
        List<DriverData> driverDataList =
                drivers.stream().map(DataObjectMapper::dataFromDriver).collect(Collectors.toList());

        model.addAttribute("driverDataList", driverDataList);

        return "/manager/drivers";
    }

}
