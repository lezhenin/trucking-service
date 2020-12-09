package trucking.model;

import org.junit.jupiter.api.Test;

import static trucking.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    @Test
    void test_createOffer() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        assertDoesNotThrow(() -> driver.createOffer(offer));

    }

    @Test
    void test_createOffer_after_accept() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        assertDoesNotThrow(() -> driver.createOffer(offer));

        Offer second_offer = generateOffer(order, driver);
        assertDoesNotThrow(() -> driver.createOffer(second_offer));

        client.acceptOffer(offer);

        Offer third_offer = generateOffer(order, driver);
        assertThrows(Exception.class, () -> driver.createOffer(third_offer));

    }


    @Test
    void test_approveContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));

    }

    @Test
    void test_refuseContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> driver.refuseContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));

    }

    @Test
    void test_completeContract_before_agreement() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }


    @Test
    void test_completeContract_after_approveContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.approveContract(contract));
        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertDoesNotThrow(() -> driver.completeContract(contract));

    }

    @Test
    void test_completeContract_after_refuseContract() throws Exception {

        Client client = generateClient();
        Driver driver = generateDriver();
        Manager manager = generateManager();

        Order order = generateOrder(client);
        client.createOrder(order);

        Offer offer = generateOffer(order, driver);
        driver.createOffer(offer);
        client.acceptOffer(offer);

        Contract contract = generateContract(order, driver, manager);
        manager.createContract(contract);

        assertDoesNotThrow(() -> client.refuseContract(contract));
        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.completeContract(contract));

    }
}
