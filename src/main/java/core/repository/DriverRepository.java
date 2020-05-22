package core.repository;

import core.model.Driver;
import core.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Optional<Driver> findDriverByVehicle(Vehicle vehicle);
    List<Driver> findAll();
}
