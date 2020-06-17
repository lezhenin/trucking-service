package trucking.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


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

}
