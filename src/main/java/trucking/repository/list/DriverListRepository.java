package trucking.repository.list;

import trucking.model.Driver;
import trucking.model.Vehicle;
import trucking.repository.DriverRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Objects;
import java.util.Optional;

@NoRepositoryBean
public class DriverListRepository extends CrudListRepository<Driver> implements DriverRepository {

    @Override
    public Optional<Driver> findDriverByVehicle(Vehicle vehicle) {
        return this.itemList.stream()
                .filter(driver -> Objects.equals(driver.getVehicle().getId(), vehicle.getId()))
                .findFirst();
    }
}
