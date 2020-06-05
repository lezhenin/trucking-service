package trucking.web.controllers.json;

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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@ResponseBody
@RequestMapping("/api/driver")
public class DriverController {

    private final DriverRepository driverRepository;
    private final ContractRepository contractRepository;

    private final UsernameIdMapper usernameIdMapper;

    public DriverController(DriverRepository driverRepository, ContractRepository contractRepository, UsernameIdMapper usernameIdMapper) {
        this.driverRepository = driverRepository;
        this.contractRepository = contractRepository;
        this.usernameIdMapper = usernameIdMapper;
    }


    @GetMapping("/orders")
    List<OrderData> getOrders(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();
        List<Order> orders =
                contractRepository.findAllByDriver(driver).stream().map(Contract::getOrder).collect(Collectors.toList());
        return orders.stream().map(DataObjectMapper::dataFromOrder).collect(Collectors.toList());
    }

    @GetMapping("/contracts")
    List<ContractData> getContracts(Principal principal) {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();
        List<Contract> contracts = contractRepository.findAllByDriver(driver);
        return contracts.stream().map(DataObjectMapper::dataFromContract).collect(Collectors.toList());
    }

    @GetMapping("/contracts/{contractId}/approve")
    ContractData approveContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractId).get();
        driver.approveContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    @GetMapping("/contracts/{contractId}/refuse")
    ContractData refuseContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractId).get();
        driver.refuseContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }

    @GetMapping("/contracts/{contractId}/complete")
    ContractData completeContract(Principal principal, @PathVariable Long contractId) throws Exception {
        Long id = usernameIdMapper.map(principal);
        Driver driver = driverRepository.findById(id).get();
        Contract contract = contractRepository.findById(contractId).get();
        driver.completeContract(contract);
        contractRepository.save(contract);
        return DataObjectMapper.dataFromContract(contract);
    }
    
}
