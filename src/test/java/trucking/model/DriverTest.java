package trucking.model;

import org.junit.jupiter.api.Test;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    @Test
    void test_approveContract() {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));

    }

    @Test
    void test_refuseContract() {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertDoesNotThrow(() -> driver.refuseContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));

    }

    @Test
    void test_completeContract_before_agreement() {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }


    @Test
    void test_completeContract_after_approveContract() {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertDoesNotThrow(() -> client.approveContract(contract));
        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertDoesNotThrow(() -> driver.completeContract(contract));

    }

    @Test
    void test_completeContract_after_refuseContract() {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertDoesNotThrow(() -> client.refuseContract(contract));
        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }
}
