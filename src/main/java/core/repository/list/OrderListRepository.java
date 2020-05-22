package core.repository.list;

import core.model.Client;
import core.model.Order;
import core.repository.OrderRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoRepositoryBean
public class OrderListRepository extends CrudListRepository<Order> implements OrderRepository {

    public List<Order> findAllByClient(final Client client) {
        return itemList.stream().
                filter(order -> Objects.equals(order.getClient().getId(), client.getId()))
                .collect(Collectors.toList());
    }
}
