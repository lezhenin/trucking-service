package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import trucking.core.model.Client;
import trucking.core.model.Contract;
import trucking.core.model.Driver;
import trucking.core.model.Order;
import trucking.core.repository.ClientRepository;
import trucking.core.repository.ContractRepository;
import trucking.core.repository.DriverRepository;
import trucking.core.repository.OrderRepository;
import trucking.web.UsernameIdMapper;
import trucking.web.data.ContractData;
import trucking.web.data.DataObjectMapper;
import trucking.web.data.NewOrderData;
import trucking.web.data.OrderData;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/driver")
public class DriverWebController {

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final ContractRepository contractRepository;

    private final UsernameIdMapper usernameIdMapper;

    public DriverWebController(OrderRepository orderRepository, DriverRepository driverRepository, ContractRepository contractRepository, UsernameIdMapper usernameIdMapper) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.contractRepository = contractRepository;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();

        List<Contract> contracts = driver.getContracts();
        List<Order> orders =
                contracts.stream().map(Contract::getOrder).collect(Collectors.toList());
        List<OrderData> orderDataList =
                orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());

        model.addAttribute("orderDataList", orderDataList);
        return "/driver/orders";
    }


    @RequestMapping({"/contracts"})
    public String contracts(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();

        List<Contract> orders = driver.getContracts();
        List<ContractData> contractDataList =
                orders.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());

        ContractData contractData = new ContractData();

        model.addAttribute("contractDataList", contractDataList);
        model.addAttribute("contractData", contractData);
        return "/driver/contracts";
    }

    @RequestMapping(value={"/contracts"}, params={"update"})
    public String updateContract(Principal principal, ContractData contractData, @RequestParam("update") String action) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractData.getId()).get();

        switch (action) {
            case "approve":
                driver.approveContract(contract);
                break;
            case "refuse":
                driver.refuseContract(contract);
                break;
            case "complete":
                driver.completeContract(contract);
                break;
        }

        return "redirect:/driver/contracts";
    }
}
