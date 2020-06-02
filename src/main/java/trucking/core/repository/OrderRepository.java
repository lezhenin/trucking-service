package trucking.core.repository;

import trucking.core.model.Client;
import trucking.core.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByClient(Client client);
    List<Order> findAll();
}
