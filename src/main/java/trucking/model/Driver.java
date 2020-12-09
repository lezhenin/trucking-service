package trucking.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;


@Entity
public class Driver extends Contractor {

    @OneToOne private Vehicle vehicle;

    public Driver(
            String firstName, String lastName, String phoneNumber, String email, Vehicle vehicle
    ) {
        super(firstName, lastName, phoneNumber, email, Contract.Role.DRIVER);
        this.vehicle = vehicle;
    }

    protected Driver() {
        super(Contract.Role.DRIVER);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }


    public void createOffer(Offer offer) throws Exception {

        Order order = offer.getOrder();
        Driver driver = offer.getDriver();

        if (!Objects.equals(driver.getId(), getId())) {
            throw new Exception("Driver is not associated with driver");
        }

        if (order.hasAcceptedOffer()) {
            throw new Exception("There is already accepted offer");
        }

        Vehicle vehicle = driver.getVehicle();
        if (order.getCargoHeight() > vehicle.getMaxCargoHeight() ||
                order.getCargoWidth() > vehicle.getMaxCargoWidth() ||
                order.getCargoLength() > vehicle.getMaxCargoLength() ||
                order.getCargoWeight() > vehicle.getMaxCargoWeight()) {
            throw new Exception("Cargo doesn't fit driver's vehicle");
        }

    }

}
