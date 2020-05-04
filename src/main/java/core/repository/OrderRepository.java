package core.repository;

import core.model.Client;
import core.model.Order;

import java.util.List;

public interface OrderRepository {

    void add(Order order);
    void remove(Order order);
    List<Order> getAll();
    List<Order> getByClient(Client client);

}
