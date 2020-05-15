package core;

import core.model.*;
import core.repository.RepositorySingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static core.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {
    private static final Manager manager = new Manager(generateContacts());
    private static final Client client = new Client(generateContacts());
    private static final Driver driver = new Driver(generateContacts(), null);


    @BeforeEach
    void init() {
        RepositorySingleton.getInstance().clear();
        RepositorySingleton.getInstance().getDriverRepository().add(driver);
    }


    @Test
    void test_approveContract() {

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

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }


    @Test
    void test_completeContract_after_approveContract() {

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

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));

        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }
}
