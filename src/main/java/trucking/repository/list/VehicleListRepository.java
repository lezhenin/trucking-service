package trucking.repository.list;

import org.springframework.data.repository.NoRepositoryBean;
import trucking.model.Vehicle;
import trucking.repository.VehicleRepository;

@NoRepositoryBean
public class VehicleListRepository extends CrudListRepository<Vehicle> implements VehicleRepository { }
