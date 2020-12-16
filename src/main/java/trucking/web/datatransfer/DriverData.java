package trucking.web.datatransfer;

import org.springframework.hateoas.RepresentationModel;

public class DriverData extends RepresentationModel<DriverData> {

    private final Long id;

    private final String firstName;
    private final String lastName;

    VehicleData vehicle;

    public DriverData(Long id, String firstName, String lastName, VehicleData vehicle) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public VehicleData getVehicle() {
        return vehicle;
    }
}
