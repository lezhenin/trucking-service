package core.repository;

import core.Client;
import core.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListOrderRepository implements OrderRepository {

    List<Order> orderList = new ArrayList<Order>();

    public void add(Order order) {
        orderList.add(order);
    }

    public void remove(Order order) {
        orderList.remove(order);
    }

    public List<Order> getAll() {
        return new ArrayList<Order>(orderList);
    }

    public List<Order> getByClient(final Client client) {
        return orderList.stream().filter(order -> order.getClient() == client).collect(Collectors.toList());
    }
}
