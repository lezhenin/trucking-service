package trucking.core.model;

import trucking.ApplicationConfiguration;
import trucking.core.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static trucking.core.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class DriverTest {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ContractRepository contractRepository;
    @Autowired private DriverRepository driverRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ManagerRepository managerRepository;

    private void setRepositories() {
        RepositorySingleton.getInstance().setOrderRepository(orderRepository);
        RepositorySingleton.getInstance().setContractRepository(contractRepository);
        RepositorySingleton.getInstance().setDriverRepository(driverRepository);
        RepositorySingleton.getInstance().setClientRepository(clientRepository);
        RepositorySingleton.getInstance().setManagerRepository(managerRepository);
    }

    @BeforeEach
    void init() {
       setRepositories();
        RepositorySingleton.getInstance().clear();
    }

    @Test
    void test_approveContract() {

        Client client = new Client(generateContacts());
        RepositorySingleton.getInstance().getClientRepository().save(client);

        Driver driver = new Driver(generateContacts(), null);
        RepositorySingleton.getInstance().getDriverRepository().save(driver);

        Manager manager = new Manager(generateContacts());
        RepositorySingleton.getInstance().getManagerRepository().save(manager);

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));

    }

    @Test
    void test_refuseContract() {

        Client client = new Client(generateContacts());
        RepositorySingleton.getInstance().getClientRepository().save(client);

        Driver driver = new Driver(generateContacts(), null);
        RepositorySingleton.getInstance().getDriverRepository().save(driver);

        Manager manager = new Manager(generateContacts());
        RepositorySingleton.getInstance().getManagerRepository().save(manager);

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> driver.refuseContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));

    }

    @Test
    void test_completeContract_before_agreement() {

        Client client = new Client(generateContacts());
        RepositorySingleton.getInstance().getClientRepository().save(client);

        Driver driver = new Driver(generateContacts(), null);
        RepositorySingleton.getInstance().getDriverRepository().save(driver);

        Manager manager = new Manager(generateContacts());
        RepositorySingleton.getInstance().getManagerRepository().save(manager);

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }


    @Test
    void test_completeContract_after_approveContract() {

        Client client = new Client(generateContacts());
        RepositorySingleton.getInstance().getClientRepository().save(client);

        Driver driver = new Driver(generateContacts(), null);
        RepositorySingleton.getInstance().getDriverRepository().save(driver);

        Manager manager = new Manager(generateContacts());
        RepositorySingleton.getInstance().getManagerRepository().save(manager);

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.approveContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));

        assertDoesNotThrow(() -> driver.completeContract(contract));

    }

    @Test
    void test_completeContract_after_refuseContract() {

        Client client = new Client(generateContacts());
        RepositorySingleton.getInstance().getClientRepository().save(client);

        Driver driver = new Driver(generateContacts(), null);
        RepositorySingleton.getInstance().getDriverRepository().save(driver);

        Manager manager = new Manager(generateContacts());
        RepositorySingleton.getInstance().getManagerRepository().save(manager);

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));

        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }
}
