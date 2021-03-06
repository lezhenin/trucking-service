package trucking.repository;

import org.springframework.stereotype.Repository;
import trucking.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    List<Vehicle> findAll();
}
