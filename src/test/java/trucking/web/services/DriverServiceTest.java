package trucking.web.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trucking.TruckingApplication;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.datatransfer.ContractData;
import trucking.web.datatransfer.OrderData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static trucking.web.services.TestUtils.*;

@SpringBootTest(classes = {TruckingApplication.class})
public class DriverServiceTest {

    @Autowired private DriverService driverService;

    @Autowired private ClientRepository clientRepository;
    @Autowired private DriverRepository driverRepository;
    @Autowired private ManagerRepository managerRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ContractRepository contractRepository;
    @Autowired private VehicleRepository vehicleRepository;

    @BeforeEach
    void prepareData() {

        contractRepository.deleteAll();
        orderRepository.deleteAll();
        driverRepository.deleteAll();
        vehicleRepository.deleteAll();
        managerRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void test_getOrders() throws Exception {

        Client client = generateClient();
        clientRepository.save(client);

        Driver driver = generateDriver();
        vehicleRepository.save(driver.getVehicle());
        driverRepository.save(driver);

        Manager manager = generateManager();
        managerRepository.save(manager);

        Order order = generateOrder(client);
        orderRepository.save(order);

        client.createOrder(order);
        orderRepository.save(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);

        List<OrderData> orders = driverService.getOrders(driver.getId());
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.get(0).getId());

    }

    @Test
    void test_getContracts() throws Exception {

        Client client = generateClient();
        clientRepository.save(client);

        Driver driver = generateDriver();
        vehicleRepository.save(driver.getVehicle());
        driverRepository.save(driver);

        Manager manager = generateManager();
        managerRepository.save(manager);

        Order order = generateOrder(client);
        orderRepository.save(order);

        client.createOrder(order);
        orderRepository.save(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);

        List<ContractData> contracts = driverService.getContracts(driver.getId());
        assertEquals(1, contracts.size());
        assertEquals(contract.getId(), contracts.get(0).getId());

    }


    @Test
    void test_approveContract() throws Exception {

        Client client = generateClient();
        clientRepository.save(client);

        Driver driver = generateDriver();
        vehicleRepository.save(driver.getVehicle());
        driverRepository.save(driver);

        Manager manager = generateManager();
        managerRepository.save(manager);

        Order order = generateOrder(client);
        orderRepository.save(order);

        client.createOrder(order);
        orderRepository.save(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Contract.Status.NONE, contract.getDriverStatus());

        driverService.approveContract(driver.getId(), contract.getId());

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Contract.Status.APPROVED, contract.getDriverStatus());

    }

    @Test
    void test_refuseContract() throws Exception {

        Client client = generateClient();
        clientRepository.save(client);

        Driver driver = generateDriver();
        vehicleRepository.save(driver.getVehicle());
        driverRepository.save(driver);

        Manager manager = generateManager();
        managerRepository.save(manager);

        Order order = generateOrder(client);
        orderRepository.save(order);

        client.createOrder(order);
        orderRepository.save(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Contract.Status.NONE, contract.getDriverStatus());

        driverService.refuseContract(driver.getId(), contract.getId());

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Contract.Status.REFUSED, contract.getDriverStatus());

    }

    @Test
    void test_completeContract() throws Exception {

        Client client = generateClient();
        clientRepository.save(client);

        Driver driver = generateDriver();
        vehicleRepository.save(driver.getVehicle());
        driverRepository.save(driver);

        Manager manager = generateManager();
        managerRepository.save(manager);

        Order order = generateOrder(client);
        orderRepository.save(order);

        client.createOrder(order);
        orderRepository.save(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);
        contractRepository.save(contract);
        orderRepository.save(order);

        client.approveContract(contract);
        contractRepository.save(contract);

        assertEquals(Contract.Status.NONE, contract.getDriverStatus());

        driverService.approveContract(driver.getId(), contract.getId());

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Contract.Status.APPROVED, contract.getDriverStatus());

        driverService.completeContract(driver.getId(), contract.getId());

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Contract.Status.COMPLETED, contract.getDriverStatus());

    }

}
