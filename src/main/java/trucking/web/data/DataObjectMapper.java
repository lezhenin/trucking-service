package trucking.web.data;

import trucking.core.model.*;

public class DataObjectMapper {

    public static OrderData dataFromOrder(Order order) {
        return new OrderData(order.getId(), order.getCargoWeight(), order.getCargoHeight(), order.getCargoWidth(),
                order.getCargoLength(), order.getLoadingAddress(), order.getLoadingAddress(), order.getDeadline(),
                order.getClient().getId(), order.getState());
    }

    public static Order orderFromData(NewOrderData data, Client client) {
        return new Order(client, data.getCargoWeight(), data.getCargoHeight(), data.getCargoWidth(),
                data.getCargoLength(), data.getLoadingAddress(), data.getLoadingAddress(), data.getDeadline());
    }

    public static ContractData dataFromContract(Contract contract) {
        return new ContractData(contract.getId(), contract.getOrder().getId(), contract.getClient().getId(), contract.getDriver().getId(),
                contract.getManager().getId(), contract.getClientStatus(), contract.getDriverStatus(), contract.getPayment());
    }

    public static Contract contractFromData(NewContractData data, Order order, Driver driver, Manager manager) {
        return new Contract(order, driver, manager, data.getPayment());
    }

}
