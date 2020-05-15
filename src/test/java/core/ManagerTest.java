package core;

import core.model.*;
import core.model.Order;
import core.repository.RepositorySingleton;
import org.junit.jupiter.api.*;

import java.util.List;

import static core.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagerTest {

    private static final Manager manager = new Manager(generateContacts());
    private static final Driver driver = new Driver(generateContacts(), null);
    private static final Client client = new Client(generateContacts());

    private static Contract contract;

    @BeforeEach
    void init() {
        RepositorySingleton.getInstance().clear();
        RepositorySingleton.getInstance().getDriverRepository().add(driver);
    }


    @Test
    void test_createContract() {

        Order order = generateOrder(client);
        client.createOrder(order);

        contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertEquals(Order.State.PROCESSED, order.getState());

        assertFalse(contract.isComplete());

        List<Contract> clientContracts = client.getContracts();
        List<Contract> driverContracts = driver.getContracts();

        assertEquals(1, clientContracts.size());
        assertEquals(1, driverContracts.size());

        assertEquals(contract, clientContracts.get(0));
        assertEquals(contract, driverContracts.get(0));
    }

    @Test
    void test_completeContract_before_completion() {

        Order order = generateOrder(client);
        client.createOrder(order);

        contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> manager.completeContract(contract));
    }

    @Test
    void test_completeContract_after_partial_completion() {

        Order order = generateOrder(client);
        client.createOrder(order);

        contract = generateContract(order, driver, manager);
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

        Order order = generateOrder(client);
        client.createOrder(order);

        contract = generateContract(order, driver, manager);
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
