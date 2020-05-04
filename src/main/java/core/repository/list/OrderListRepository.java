package core.repository.list;

import core.model.Client;
import core.model.Order;
import core.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderListRepository
        extends ListRepository<Order>
        implements OrderRepository {

    @Override
    public List<Order> getByClient(final Client client) {
        return itemList.stream().
                filter(order -> order.getClient().getId() == client.getId())
                .collect(Collectors.toList());
    }
}
