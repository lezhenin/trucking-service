package trucking.web.controllers.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import trucking.model.Contract;
import trucking.model.Driver;
import trucking.model.Order;
import trucking.repository.ContractRepository;
import trucking.repository.DriverRepository;
import trucking.web.security.UsernameIdMapper;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.DataObjectMapper;
import trucking.web.datatransfer.OrderData;
import trucking.web.services.DriverService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@ResponseBody
@RequestMapping("/api/driver")
public class DriverController {

    private final DriverService driverService;
    private final UsernameIdMapper usernameIdMapper;

    public DriverController(
            @Autowired DriverService driverService,
            @Autowired UsernameIdMapper usernameIdMapper
    ) {
        this.driverService = driverService;
        this.usernameIdMapper = usernameIdMapper;
    }

    @GetMapping("/orders")
    List<OrderData> getOrders(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return driverService.getOrders(id);
    }

    @GetMapping("/contracts")
    List<ContractData> getContracts(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        return driverService.getContracts(id);
    }

    @GetMapping("/contracts/{contractId}/approve")
    ContractData approveContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return driverService.approveContract(id, contractId);
    }

    @GetMapping("/contracts/{contractId}/refuse")
    ContractData refuseContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return driverService.refuseContract(id, contractId);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        return driverService.completeContract(id, contractId);
    }
    
}
