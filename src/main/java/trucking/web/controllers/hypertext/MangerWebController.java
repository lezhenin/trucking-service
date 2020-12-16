package trucking.web.controllers.hypertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;
import trucking.web.services.ManagerService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("old/manager")
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
    public String orders(Model model) {
        List<OrderData> orderDataList = managerService.getOrders();
        model.addAttribute("orderDataList", orderDataList);
        return "/manager/orders";
    }

    @RequestMapping({"/drivers"})
    public String drivers(Model model) {
        List<DriverData> driverDataList = managerService.getDrivers();
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
    public String createContract(Principal principal, NewContractData newContractData) throws Exception {
        Long id = usernameIdMapper.map(principal);
        managerService.createContract(id, newContractData);
        return "redirect:/manager/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"remove"})
    public String removeContract(Principal principal, long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        managerService.removeContract(id, contractId);
        return "redirect:/manager/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"complete"})
    public String createContract(Principal principal, long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        managerService.completeContract(id, contractId);
        return "redirect:/manager/contracts";
    }

}
