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

    static Contract generateContract(Order order, Driver driver, Manager manager) {
        Contract contract = new Contract(order, driver, manager, 1000);
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
