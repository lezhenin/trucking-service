package trucking.model;

import org.junit.jupiter.api.*;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;


public class ManagerTest {


    @Test
    void test_createContract_without_offer() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Contract contract = generateContract(order, driver, manager);

        assertThrows(Exception.class, () -> manager.createContract(contract));
        assertEquals(Order.State.PUBLISHED, order.getState());

    }

    @Test
    void test_createContract_wring_driver() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Driver second_driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);

        Offer second_offer = generateOffer(order, second_driver);
        second_driver.createOffer(second_offer);

        client.acceptOffer(offer);

        Contract contract = generateContract(order, second_driver, manager);

        assertThrows(Exception.class, () -> manager.createContract(contract));
        assertEquals(Order.State.PUBLISHED, order.getState());

    }

    @Test
    void test_createContract_wring_payment() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver, 1000);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager, 999);

        assertThrows(Exception.class, () -> manager.createContract(contract));
        assertEquals(Order.State.PUBLISHED, order.getState());

    }


    @Test
    void test_createContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertEquals(Order.State.PROCESSED, order.getState());
        assertFalse(contract.isCompleted());

    }

    @Test
    void test_removeContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertEquals(Order.State.PROCESSED, order.getState());
        assertFalse(contract.isCompleted());

        assertThrows(Exception.class, () -> manager.removeContract(contract));

        client.approveContract(contract);
        assertThrows(Exception.class, () -> manager.removeContract(contract));

        driver.refuseContract(contract);

        assertDoesNotThrow(() -> manager.removeContract(contract));

        assertEquals(Order.State.PUBLISHED, order.getState());
        assertFalse(contract.isCompleted());

    }

    @Test
    void test_createContract_afterCompleteContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);

        assertDoesNotThrow(() -> manager.createContract(contract));

        assertEquals(Order.State.PROCESSED, order.getState());
        assertFalse(contract.isCompleted());

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

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

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

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

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

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

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
