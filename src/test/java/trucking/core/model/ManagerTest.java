package trucking.core.model;

import trucking.ApplicationConfiguration;
import trucking.core.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static trucking.core.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class ManagerTest {

    @BeforeEach
    void init() {
        RepositorySingleton.getInstance().clear();
    }


    @Test
    void test_createContract() {

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

        assertEquals(Order.State.PROCESSED, order.getState());

        assertFalse(contract.isCompleted());

        List<Contract> clientContracts = client.getContracts();
        List<Contract> driverContracts = driver.getContracts();

        assertEquals(1, clientContracts.size());
        assertEquals(1, driverContracts.size());

        assertEquals(contract.getId(), clientContracts.get(0).getId());
        assertEquals(contract.getId(), driverContracts.get(0).getId());
    }

    @Test
    void test_completeContract_before_completion() {

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

        assertThrows(Exception.class, () -> manager.completeContract(contract));
    }

    @Test
    void test_completeContract_after_partial_completion() {

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

        assertDoesNotThrow(() -> {
            client.approveContract(contract);
            driver.approveContract(contract);
        });

        assertDoesNotThrow(() -> {
            client.completeContract(contract);
        });

        assertThrows(Exception.class, () -> manager.completeContract(contract));

    }

    @Test
    void test_completeContract_after_completion() {

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

        assertDoesNotThrow(() -> {
            client.approveContract(contract);
            driver.approveContract(contract);
        });

        assertDoesNotThrow(() -> {
            client.completeContract(contract);
            driver.completeContract(contract);
        });

        assertDoesNotThrow(() -> manager.completeContract(contract));

    }
}
