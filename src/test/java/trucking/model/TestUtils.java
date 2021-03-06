package trucking.model;

class TestUtils {

    private static Long idCounter = 1L;

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

    static Offer generateOffer(Order order, Driver driver, int payment) {
        Offer offer = new Offer(order, driver, payment, false);
        offer.setId(idCounter++);
        return offer;
    }


    static Offer generateOffer(Order order, Driver driver) {
        return generateOffer(order, driver, 1000);
    }

    static Contract generateContract(Order order, Driver driver, Manager manager) {
        return generateContract(order, driver, manager, 1000);
    }

    static Contract generateContract(Order order, Driver driver, Manager manager, int payment) {
        Contract contract = new Contract(order, driver, manager, payment);
        contract.setId(idCounter++);
        return contract;
    }
    
    static Client generateClient() {
        Long id = idCounter++;
        Client client = new Client("name" + id, "lastname" + id, "8800"+ id, id + "@m.com");
        client.setId(id);
        return client;
    }

    static Driver generateDriver() {
        Long id = idCounter++;
        Driver driver = new Driver("name" + id, "lastname" + id, "8800"+ id, id + "@m.com", generateVehicle());
        driver.setId(id);
        return driver;
    }
    
    static Manager generateManager() {
        Long id = idCounter++;
        Manager manager = new Manager("name" + id, "lastname" + id, "8800"+ id, id + "@m.com");
        manager.setId(id);
        return manager;
    }

    static Vehicle generateVehicle() {
        Long id = idCounter++;
        Vehicle vehicle = new Vehicle("v" + id, 15, 150, 150, 150);
        vehicle.setId(id);
        return vehicle;
    }
}
