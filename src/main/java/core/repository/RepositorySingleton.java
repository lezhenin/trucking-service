package core.repository;

public class RepositorySingleton {

    private static RepositorySingleton instance;
    private final OrderRepository orderRepository = new OrderRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final DriverRepository driverRepository = new DriverRepository();


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

    public ContractRepository getContractRepository() {
        return contractRepository;
    }

    public DriverRepository getDriverRepository() {
        return driverRepository;
    }
}
