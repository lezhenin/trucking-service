package core.repository;

import core.Client;
import core.Order;

import java.util.List;

public interface OrderRepository {

    void add(Order order);
    void remove(Order order);
    List<Order> getAll();
    List<Order> getByClient(Client client);

}
