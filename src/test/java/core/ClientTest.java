package core;

import core.model.*;
import core.repository.RepositorySingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static core.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    private static final Manager manager = new Manager(generateContacts());
    private static final Client client = new Client(generateContacts());
    private static final Driver driver = new Driver(generateContacts(), null);


    @BeforeEach
    void init() {
        RepositorySingleton.getInstance().clear();
        RepositorySingleton.getInstance().getDriverRepository().add(driver);
    }

    @Test
    void test_removeOrder() {

        Order order = generateOrder(client);
        client.createOrder(order);

        assertEquals(Order.State.PUBLISHED, order.getState());

        List<Order> orders = client.getOrders();
        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));

    }

    @Test
    void test_createOrder_before_processing() {

        Order order = generateOrder(client);
        client.createOrder(order);

        assertDoesNotThrow(() -> client.removeOrder(order));

        List<Order> orders = client.getOrders();
        assertEquals(0, orders.size());

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

    }

    @Test
    void test_createOrder_after_processing() {

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> client.removeOrder(order));

        List<Order> orders = client.getOrders();
        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));

    }

    @Test
    void test_approveContract() {

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.approveContract(contract));
        assertThrows(Exception.class, () -> client.approveContract(contract));
        assertThrows(Exception.class, () -> client.refuseContract(contract));

    }

    @Test
    void test_refuseContract() {

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));
        assertThrows(Exception.class, () -> client.refuseContract(contract));
        assertThrows(Exception.class, () -> client.approveContract(contract));

    }

    @Test
    void test_completeContract_before_agreement() {

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> client.completeContract(contract));

    }


    @Test
    void test_completeContract_after_approveContract() {

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.approveContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));

        assertDoesNotThrow(() -> client.completeContract(contract));

    }

    @Test
    void test_completeContract_after_refuseContract() {

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));

        assertThrows(Exception.class, () -> client.completeContract(contract));

    }


}
