package trucking.web.controllers.hypertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;
import trucking.web.services.ManagerService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class MangerWebController {

    private final ManagerService managerService;
    private final UsernameIdMapper usernameIdMapper;

    public MangerWebController(
            @Autowired ManagerService managerService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.managerService = managerService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<OrderData> orderDataList = managerService.getOrders(id);
        model.addAttribute("orderDataList", orderDataList);
        return "/manager/orders";
    }

    @RequestMapping({"/drivers"})
    public String drivers(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<DriverData> driverDataList = managerService.getDrivers(id);
        model.addAttribute("driverDataList", driverDataList);
        return "/manager/drivers";
    }

    @RequestMapping({"/contracts"})
    public String contracts(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<ContractData> contractDataList = managerService.getContracts(id);
        NewContractData newContractData = new NewContractData();
        model.addAttribute("contractDataList", contractDataList);
        model.addAttribute("newContractData", newContractData);
        return "/manager/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"create"})
    @Transactional
    public String createContract(Principal principal, NewContractData newContractData) {
        Long id = usernameIdMapper.map(principal);
        managerService.createContract(id, newContractData);
        return "redirect:/manager/contracts";
    }

}
