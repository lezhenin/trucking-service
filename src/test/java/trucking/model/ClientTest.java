package trucking.model;

import org.junit.jupiter.api.Test;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    
    @Test
    void test_createOrder() {

        Client client = new Client(generateContacts());
        client.setId(0L);
        
        Order order = generateOrder(client);
        order.setId(1L);
        assertDoesNotThrow(() -> client.createOrder(order));

    }

    @Test
    void test_removeOrder_before_processing() {

        Client client = generateClient();
        Order order = generateOrder(client);
        client.createOrder(order);

        assertDoesNotThrow(() -> client.removeOrder(order));
        
    }

    @Test
    void test_removeOrder_after_processing() {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> client.removeOrder(order));

    }

    @Test
    void test_approveContract() {


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
    void test_refuseContract() {

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
    void test_completeContract_before_agreement() {

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
    void test_completeContract_after_approveContract() {

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
    void test_completeContract_after_refuseContract() {

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
