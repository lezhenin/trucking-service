package trucking.core.repository.list;

import trucking.core.model.Driver;
import trucking.core.model.Vehicle;
import trucking.core.repository.DriverRepository;
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
