package trucking.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Manager extends Person {

    public Manager(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    protected Manager() { super(); }

    public void createContract(Contract contract) throws Exception {
        Order order = contract.getOrder();

        if (order.getState() != Order.State.PUBLISHED) {
            throw new Exception("Order already has been processed/completed");
        }

        if (order.getState() == Order.State.REMOVED) {
            throw new Exception("Order already has been removed");
        }

        Vehicle vehicle = contract.getDriver().getVehicle();
        if (order.getCargoHeight() > vehicle.getMaxCargoHeight() ||
                order.getCargoWidth() > vehicle.getMaxCargoWidth() ||
                order.getCargoLength() > vehicle.getMaxCargoLength() ||
                order.getCargoWeight() > vehicle.getMaxCargoWeight()) {
            throw new Exception("Cargo doesn't fit driver's vehicle");
        }

        order.setState(Order.State.PROCESSED);
    }

    public void removeContract(Contract contract) throws Exception {
        if (!contract.isRefused()) {
            throw new Exception("Contract haven't been refused");
        }
        Order order = contract.getOrder();
        order.setState(Order.State.PUBLISHED);
    }

    public void completeContract(Contract contract) throws Exception {

        if (!Objects.equals(contract.getManager().getId(), getId())) {
            throw new Exception("Manager is not associated with contract");
        }

        if (!contract.isCompleted()) {
            throw new Exception("Contractors haven't completed contract yet");
        }

        Order order = contract.getOrder();
        order.setState(Order.State.COMPLETED);
    }

}
