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

        if (order.getState() == Order.State.COMPLETED) {
            throw new Exception("Order already has been completed");
        }

        Vehicle vehicle = contract.getDriver().getVehicle();
        if (order.getCargoHeight() > vehicle.getMaxCargoHeight() ||
                order.getCargoWidth() > vehicle.getMaxCargoHeight() ||
                order.getCargoLength() > vehicle.getMaxCargoHeight() ||
                order.getCargoWeight() > vehicle.getMaxCargoHeight()) {
            throw new Exception("Cargo doesn't fit driver's vehicle");
        }

        order.setState(Order.State.PROCESSED);
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
