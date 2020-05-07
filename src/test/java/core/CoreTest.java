package core;

import core.model.*;
import core.repository.RepositorySingleton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoreTest {

    private static int contactCounter = 0;

    private static Contacts generateContacts() {
        int number = contactCounter++;
        return new Contacts(
                "name" + number,
                "lastname" + number,
                "8800"+ number,
                number + "@m.com"
                );
    }


    private static final Manager manager = new Manager(generateContacts());
    private static final Client client = new Client(generateContacts());
    private static final Driver driver = new Driver(generateContacts(), null);

    @BeforeAll
    static void init() {
        RepositorySingleton.getInstance().getDriverRepository().add(driver);
    }

    @Test
    void test(){

        Order order = new Order(
                client,
                10,
                100,
                100,
                100,
                "street 1",
                "street 2",
                "25.05.20"
        );

        client.createOrder(order);
        assertEquals(1, client.getOrders().size());

        assertEquals(order.getState(), Order.State.PUBLISHED);

        assertEquals(1, manager.getOrders().size());
        assertEquals(1, manager.getDrivers().size());

        Contract contract = new Contract(order, driver, manager, 1000);
        manager.createContract(contract);

        assertEquals(order.getState(), Order.State.PROCESSED);

        assertEquals(1, client.getContracts().size());
        assertEquals(1, driver.getContracts().size());

        assertThrows(Exception.class, () -> client.completeContract(contract));
        assertThrows(Exception.class, () -> driver.completeContract(contract));

        assertThrows(Exception.class, () -> manager.completeContract(contract));

        assertDoesNotThrow(() -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.approveContract(contract));
        assertThrows(Exception.class, () -> driver.refuseContract(contract));

        assertThrows(Exception.class, () -> client.completeContract(contract));
        assertThrows(Exception.class, () -> driver.completeContract(contract));

        assertDoesNotThrow(() -> client.approveContract(contract));
        assertThrows(Exception.class, () -> client.approveContract(contract));
        assertThrows(Exception.class, () -> client.refuseContract(contract));

        assertDoesNotThrow(() -> client.completeContract(contract));
        assertDoesNotThrow(() -> driver.completeContract(contract));

        assertThrows(Exception.class, () -> client.completeContract(contract));
        assertThrows(Exception.class, () -> driver.completeContract(contract));

        assertDoesNotThrow(() -> manager.completeContract(contract));

        assertEquals(order.getState(), Order.State.COMPLETED);
    }

}
