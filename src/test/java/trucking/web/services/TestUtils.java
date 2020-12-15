package trucking.web.services;

import trucking.model.*;

class TestUtils {

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

    static Offer generateOffer(Order order, Driver driver) {
        return new Offer(order, driver, 1000, false);
    }

    static Contract generateContract(Order order, Driver driver, Manager manager) {
        return new Contract(order, driver, manager, 1000);
    }
    
    static Client generateClient() {
        return new Client("name", "lastname", "8800",  "e@m.com");
    }

    static Driver generateDriver() {
        return new Driver("name", "lastname", "8800", "e@m.com", generateVehicle());
    }
    
    static Manager generateManager() {
        return new Manager("name", "lastname", "8800", "e@m.com");
    }

    static Vehicle generateVehicle() {
       return new Vehicle("v", 15, 150, 150, 150);
    }
}
