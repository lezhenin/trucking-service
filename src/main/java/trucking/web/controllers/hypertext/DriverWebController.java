package trucking.web.controllers.hypertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.OrderData;
import trucking.web.services.DriverService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/old/driver")
public class DriverWebController {

    private final DriverService driverService;
    private final UsernameIdMapper usernameIdMapper;

    public DriverWebController(
            @Autowired DriverService driverService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.driverService = driverService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @RequestMapping({"/orders"})
    public String orders(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<OrderData> orderDataList = driverService.getOrders(id);
        model.addAttribute("orderDataList", orderDataList);
        return "/driver/orders";
    }

    @RequestMapping({"/contracts"})
    public String contracts(Principal principal, Model model) {
        Long id = usernameIdMapper.map(principal);
        List<ContractData> contractDataList = driverService.getContracts(id);
        ContractData contractData = new ContractData();
        model.addAttribute("contractDataList", contractDataList);
        model.addAttribute("contractData", contractData);
        return "/driver/contracts";
    }

    @RequestMapping(value = {"/contracts"}, params = {"update"})
    public String updateContract(Principal principal, long contractId, @RequestParam("update") String action) throws Exception {
        Long id = usernameIdMapper.map(principal);
        //noinspection IfCanBeSwitch
        if (action.equals("approve")) {
            driverService.approveContract(id, contractId);
        } else if (action.equals("refuse")) {
            driverService.refuseContract(id, contractId);
        } else if (action.equals("complete")) {
            driverService.completeContract(id, contractId);
        }
        return "redirect:/driver/contracts";
    }
}
