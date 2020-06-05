package trucking.repository;

import trucking.model.Client;
import trucking.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByClient(Client client);
    List<Order> findAll();
}
