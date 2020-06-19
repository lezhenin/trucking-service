package trucking.model;

import org.junit.jupiter.api.*;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;


public class ManagerTest {


    @Test
    void test_createContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertEquals(Order.State.PROCESSED, order.getState());
        assertFalse(contract.isCompleted());

    }

    @Test
    void test_createContract_afterCompleteContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertEquals(Order.State.PROCESSED, order.getState());
        assertFalse(contract.isCompleted());

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertDoesNotThrow(() -> {
            client.approveContract(contract);
            driver.approveContract(contract);
        });

        assertDoesNotThrow(() -> {
            client.completeContract(contract);
            driver.completeContract(contract);
        });

        assertDoesNotThrow(() -> manager.completeContract(contract));

        assertThrows(Exception.class, () -> manager.createContract(contract));

    }

    @Test
    void test_completeContract_before_completion() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertThrows(Exception.class, () -> manager.completeContract(contract));
    }

    @Test
    void test_completeContract_after_partial_completion() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

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
    void test_completeContract_after_completion() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

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
