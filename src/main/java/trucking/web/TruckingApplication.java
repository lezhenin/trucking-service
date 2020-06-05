package trucking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import trucking.core.model.Client;
import trucking.core.model.Contacts;
import trucking.core.model.Manager;
import trucking.core.model.Order;
import trucking.core.repository.*;


@SpringBootApplication
@ComponentScan(basePackages = "trucking")
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
            ManagerRepository managerRepository, UsernameIdMapper usernameIdMapper
    ) {
        return args -> {

            Contacts clientContacts = new Contacts("client", "number1", "c", "d");
            Client client = new Client(clientContacts);
            clientRepository.save(client);
            usernameIdMapper.put("client", client.getId());

            client.createOrder(
                    new Order(client, 5, 5, 5, 5, "a", "b", "d")
            );
            client.createOrder(
                    new Order(client, 5, 51, 5, 5, "b", "a", "d")
            );

            Contacts managerContacts = new Contacts("manager", "number1", "c", "d");
            Manager manager = new Manager(managerContacts);
            managerRepository.save(manager);
            usernameIdMapper.put("manager", manager.getId());

        };
    }

    public static void main(String ...args) {
        SpringApplication.run(TruckingApplication.class, args);
    }

}
