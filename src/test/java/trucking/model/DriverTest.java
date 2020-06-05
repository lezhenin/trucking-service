package trucking.model;

import trucking.JpaConfiguration;
import trucking.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { JpaConfiguration.class })
public class DriverTest {

    @BeforeEach
    void init() {
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
