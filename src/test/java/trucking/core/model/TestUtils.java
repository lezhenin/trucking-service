package trucking.core.model;

public class TestUtils {

    private static int contactCounter = 0;

    static Contacts generateContacts() {
        int number = contactCounter++;
        return new Contacts(
                "user" + number,
                "name" + number,
                "lastname" + number,
                "8800"+ number,
                number + "@m.com"
        );
    }


    static Order generateOrder(Client client) {
        return new Order(
                client,
                10,
                100,
                100,
                100,
                "street 1",
                "street 2",
                "25.05.20"
        );

    }

    static Contract generateContract(Order order, Driver driver, Manager manager) {
        return new Contract(order, driver, manager, 1000);
    }

}
