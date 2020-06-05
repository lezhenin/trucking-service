package trucking.model;

public class TestUtils {

    private static int contactCounter = 0;
    private static Long idCounter = 1L;

    static Contacts generateContacts() {
        int number = contactCounter++;
        return new Contacts(
                "name" + number,
                "lastname" + number,
                "8800"+ number,
                number + "@m.com"
        );
    }


    static Order generateOrder(Client client) {
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
        order.setId(idCounter++);
        return order;
    }

    static Contract generateContract(Order order, Driver driver, Manager manager) {
        Contract contract = new Contract(order, driver, manager, 1000);
        contract.setId(idCounter++);
        return contract;
    }
    
    static Client generateClient() {
        Client client = new Client(generateContacts());
        client.setId(idCounter++);
        return client;
    }

    static Driver generateDriver() {
        Driver driver = new Driver(generateContacts(), null);
        driver.setId(idCounter++);
        return driver;
    }
    
    static Manager generateManager() {
        Manager manager = new Manager(generateContacts());
        manager.setId(idCounter++);
        return manager;
    }

}
