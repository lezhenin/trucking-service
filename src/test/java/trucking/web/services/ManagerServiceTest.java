package trucking.web.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trucking.TruckingApplication;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.datatransfer.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static trucking.web.services.TestUtils.*;

@SpringBootTest(classes = {TruckingApplication.class})
public class ManagerServiceTest {

    @Autowired private ManagerService managerService;

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
    void test_getOrders() {

        Client client = generateClient();
        clientRepository.save(client);

        Order order = generateOrder(client);
        orderRepository.save(order);

        Manager manager = generateManager();
        managerRepository.save(manager);

        List<OrderData> orders = managerService.getOrders();
        assertEquals(1, orders.size());
        assertEquals(order.getId(), orders.get(0).getId());

    }

    @Test
    void test_getDrivers() {

        Driver driver = generateDriver();
        vehicleRepository.save(driver.getVehicle());
        driverRepository.save(driver);

        Manager manager = generateManager();
        managerRepository.save(manager);

        List<DriverData> drivers = managerService.getDrivers();
        assertEquals(1, drivers.size());
        assertEquals(driver.getId(), drivers.get(0).getId());

    }

    @Test
    void test_createContract() throws Exception {

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

        NewContractData newContractData =
                new NewContractData(order.getId(), client.getId(), driver.getId(), 1000);

        order = orderRepository.findById(order.getId()).get();
        assertEquals(Order.State.PUBLISHED, order.getState());

        ContractData contractData = managerService.createContract(manager.getId(), newContractData);

        assertTrue(contractRepository.existsById(contractData.getId()));

        order = orderRepository.findById(order.getId()).get();
        assertEquals(Order.State.PROCESSED, order.getState());

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

        List<ContractData> contracts = managerService.getContracts(manager.getId());
        assertEquals(1, contracts.size());
        assertEquals(contract.getId(), contracts.get(0).getId());

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

        driver.approveContract(contract);
        contractRepository.save(contract);

        client.approveContract(contract);
        contractRepository.save(contract);

        driver.completeContract(contract);
        contractRepository.save(contract);

        client.completeContract(contract);
        contractRepository.save(contract);

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Order.State.PROCESSED, contract.getOrder().getState());

        managerService.completeContract(manager.getId(), contract.getId());

        contract = contractRepository.findById(contract.getId()).get();
        assertEquals(Order.State.COMPLETED, contract.getOrder().getState());

    }

}
