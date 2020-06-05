package trucking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;
import trucking.model.*;
import trucking.repository.*;
import trucking.web.security.UsernameIdMapper;


@SpringBootApplication
//@ComponentScan(value = "trucking.configuration")
public class TruckingApplication {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {

            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setBasePath("/api/rest");
                config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ALL);
            }
        };
    }

    @Bean
    public CommandLineRunner run2(
            OrderRepository orderRepository, ClientRepository clientRepository,
            DriverRepository driverRepository, ContractRepository contractRepository,
            ManagerRepository managerRepository, VehicleRepository vehicleRepository,
            UsernameIdMapper usernameIdMapper
    ) {
        return args -> {

            Contacts clientContacts = new Contacts("client", "number1", "c", "d");
            Client client = new Client(clientContacts);
            clientRepository.save(client);
            usernameIdMapper.put("client", client.getId());

            Order order = new Order(client, 5, 5, 5, 5, "a", "b", "d");
            client.createOrder(order);
            Order order1 = new Order(client, 5, 51, 5, 5, "b", "a", "d");
            client.createOrder(order1);

            Vehicle vehicle = new Vehicle("scania", 5000, 200, 200, 300);
            vehicleRepository.save(vehicle);

            Contacts driverContacts = new Contacts("driver", "number1", "c", "d");
            Driver driver = new Driver(driverContacts, vehicle);
            driverRepository.save(driver);
            usernameIdMapper.put("driver", driver.getId());

            System.out.println(driver.getId());

            Contacts managerContacts = new Contacts("manager", "number1", "c", "d");
            Manager manager = new Manager(managerContacts);
            managerRepository.save(manager);
            usernameIdMapper.put("manager", manager.getId());

            manager.createContract(new Contract(order, driver, manager, 1000));

        };
    }

    public static void main(String ...args) {
        SpringApplication.run(TruckingApplication.class, args);
    }

}
