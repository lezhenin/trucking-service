package trucking.web.datatransfer;

import trucking.model.*;

public class DataObjectMapper {

    public static OrderData dataFromOrder(Order order) {
        return new OrderData(order.getId(), order.getCargoWeight(), order.getCargoHeight(), order.getCargoWidth(),
                order.getCargoLength(), order.getShippingAddress(), order.getLoadingAddress(), order.getDeadline(),
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

    public static VehicleData dataFromVehicle(Vehicle vehicle) {
        return new VehicleData(vehicle.getModel(), vehicle.getMaxCargoWeight(), vehicle.getMaxCargoWeight(),
                vehicle.getMaxCargoWidth(), vehicle.getMaxCargoLength());
    }

    public static DriverData dataFromDriver(Driver driver) {
        return new DriverData(driver.getId(), driver.getFirstName(), driver.getLastName(),
                dataFromVehicle(driver.getVehicle()));
    }

    public static OfferData dataFromOffer(Offer offer) {
        return new OfferData(offer.getId(), offer.getDriver().getId(), offer.getOrder().getId(),
                offer.getOrder().getClient().getId(), offer.getPrice(), offer.isAccepted());
    }

    public static Offer offerFromData(NewOfferData offer, Order order, Driver driver) {
        return new Offer(order, driver, offer.getPrice(), false);
    }

}
