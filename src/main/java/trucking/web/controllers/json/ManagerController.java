package trucking.web.controllers.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.*;
import trucking.web.services.ManagerService;

import java.security.Principal;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final UsernameIdMapper usernameIdMapper;

    public ManagerController(
            @Autowired ManagerService managerService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.managerService = managerService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @GetMapping("/contracts")
    List<ContractData> getContracts(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return managerService.getContracts(id);
    }

    @PostMapping("/createContract")
    ContractData createOrder(Principal principal, @RequestBody NewContractData contractData) {
        Long id = usernameIdMapper.map(principal);
        return managerService.createContract(id, contractData);
    }

    @GetMapping("/orders")
    List<OrderData> getOrders(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return managerService.getOrders(id);
    }

    @GetMapping("/drivers")
    List<DriverData> getDrivers(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return managerService.getDrivers(id);
    }

}
