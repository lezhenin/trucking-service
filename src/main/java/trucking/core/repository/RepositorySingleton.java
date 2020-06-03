package trucking.core.repository;


public class RepositorySingleton {

    private static RepositorySingleton instance;

    private OrderRepository orderRepository;
    private ContractRepository contractRepository;
    private DriverRepository driverRepository;
    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;
    private VehicleRepository vehicleRepository;

    private RepositorySingleton() { }

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

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public ManagerRepository getManagerRepository() {
        return managerRepository;
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void clear() {
        contractRepository.deleteAll();
        orderRepository.deleteAll();
        driverRepository.deleteAll();
        clientRepository.deleteAll();
        managerRepository.deleteAll();
    }

}
