package core.repository;

public class RepositorySingleton {

    private static RepositorySingleton instance;
    private OrderRepository orderRepository = new ListOrderRepository();


    private RepositorySingleton(){}

    public static RepositorySingleton getInstance() {
        if (instance == null) {
            instance = new RepositorySingleton();
        }
        return instance;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository newRepository) {
        orderRepository = newRepository;
    }
}
