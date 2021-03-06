package trucking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.security.UsernameIdMapper;


@SpringBootApplication
public class TruckingApplication {

    @Bean
    public CommandLineRunner initModelData(
            OrderRepository orderRepository, ClientRepository clientRepository,
            DriverRepository driverRepository, ContractRepository contractRepository,
            ManagerRepository managerRepository, VehicleRepository vehicleRepository,
            OfferRepository offerRepository,
            UsernameIdMapper usernameIdMapper
    ) {
        return args -> {

            Client client = new Client("client", "number1", "c", "d");
            clientRepository.save(client);
            usernameIdMapper.put("client", client.getId());

            Order order = new Order(client, 5, 5, 5, 5, "a", "b", "d");
            client.createOrder(order);
            orderRepository.save(order);
            Order order1 = new Order(client, 5, 51, 5, 5, "b", "a", "d");
            client.createOrder(order1);
            orderRepository.save(order1);

            Vehicle vehicle = new Vehicle("scania", 5000, 200, 200, 300);
            vehicleRepository.save(vehicle);

            Driver driver = new Driver("driver", "number1", "c", "d", vehicle);
            driverRepository.save(driver);
            usernameIdMapper.put("driver", driver.getId());

            Vehicle secondVehicle = new Vehicle("mercedes", 6500, 200, 200, 350);
            vehicleRepository.save(secondVehicle);

            Driver secondDriver = new Driver("driver", "number2", "f", "e", secondVehicle);
            driverRepository.save(secondDriver);
            usernameIdMapper.put("other-driver", secondDriver.getId());


            Manager manager = new Manager("manager", "number1", "c", "d");
            managerRepository.save(manager);
            usernameIdMapper.put("manager", manager.getId());

            Offer offer = new Offer(order, driver, 1000, false);
            offerRepository.save(offer);

            driver.createOffer(offer);
            client.acceptOffer(offer);
            offerRepository.save(offer);
            orderRepository.save(order);

            Contract contract = new Contract(order, driver, manager, 1000);
            manager.createContract(contract);
            orderRepository.save(order);
            contractRepository.save(contract);

        };
    }

    public static void main(String ...args) {
        SpringApplication.run(TruckingApplication.class, args);
    }

}
