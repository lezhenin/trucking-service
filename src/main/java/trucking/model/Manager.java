package trucking.model;

import javax.persistence.Entity;

@Entity
public class Manager extends Person {

    public Manager(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    protected Manager() { super(); }

    public void createContract(Contract contract) throws Exception {
        Order order = contract.getOrder();

        if (order.getState() == Order.State.COMPLETED) {
            throw new Exception("Can't create contract: order already was completed");
        }

        Vehicle vehicle = contract.getDriver().getVehicle();
        if (order.getCargoHeight() > vehicle.getMaxCargoHeight() ||
                order.getCargoWidth() > vehicle.getMaxCargoHeight() ||
                order.getCargoLength() > vehicle.getMaxCargoHeight() ||
                order.getCargoWeight() > vehicle.getMaxCargoHeight()) {
            throw new Exception("Can't create contract: cargo doesn't fit driver's vehicle");
        }

        order.setState(Order.State.PROCESSED);
    }

    // todo mb rename: this complete is not the same as Client's or Driver's complete
    public void completeContract(Contract contract) throws Exception {
        if (!contract.isCompleted()) {
            throw new Exception();
        }

        Order order = contract.getOrder();
        order.setState(Order.State.COMPLETED);
    }

}
