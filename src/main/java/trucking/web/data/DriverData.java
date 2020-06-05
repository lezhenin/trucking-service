package trucking.web.data;

import trucking.core.model.Vehicle;

public class DriverData {

    private Long id;

    private String firstName;
    private String lastName;

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
