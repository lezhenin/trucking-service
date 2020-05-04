package core.repository;

import core.Client;
import core.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListOrderRepository extends ListRepository<Order> implements OrderRepository {

    public List<Order> getByClient(final Client client) {
        return itemList.stream().filter(order -> order.getClient() == client).collect(Collectors.toList());
    }
}
