package trucking.model;

import org.junit.jupiter.api.Test;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    
    @Test
    void test_createOrder() {

        Client client = generateClient();
        Order order = generateOrder(client);

        assertDoesNotThrow(() -> client.createOrder(order));

    }

    @Test
    void test_removeOrder_before_processing() throws Exception {

        Client client = generateClient();
        Order order = generateOrder(client);
        client.createOrder(order);

        assertDoesNotThrow(() -> client.removeOrder(order));
        assertEquals(Order.State.REMOVED, order.getState());
        assertThrows(Exception.class, () -> client.removeOrder(order));
        assertEquals(Order.State.REMOVED, order.getState());
        
    }

    @Test
    void test_removeOrder_after_processing() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));
        assertThrows(Exception.class, () -> client.removeOrder(order));
        assertEquals(Order.State.PROCESSED, order.getState());

    }

    @Test
    void test_approveContract() throws Exception {


        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.approveContract(contract));
        assertThrows(Exception.class, () -> client.approveContract(contract));
        assertThrows(Exception.class, () -> client.refuseContract(contract));

    }

    @Test
    void test_refuseContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));
        assertThrows(Exception.class, () -> client.refuseContract(contract));
        assertThrows(Exception.class, () -> client.approveContract(contract));

    }

    @Test
    void test_completeContract_before_agreement() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> client.completeContract(contract));

    }


    @Test
    void test_completeContract_after_approveContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.approveContract(contract));
        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertDoesNotThrow(() -> client.completeContract(contract));

    }

    @Test
    void test_completeContract_after_refuseContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));
        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> client.completeContract(contract));

    }


}
