package core.repository;

import core.model.Client;
import core.model.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRepository
        extends ListRepository<Order> {

    public List<Order> getByClient(final Client client) {
        return itemList.stream().
                filter(order -> order.getClient().getId() == client.getId())
                .collect(Collectors.toList());
    }
}
