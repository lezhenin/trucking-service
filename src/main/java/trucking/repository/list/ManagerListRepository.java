package trucking.repository.list;

import trucking.model.Manager;
import trucking.repository.ManagerRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class ManagerListRepository extends CrudListRepository<Manager> implements ManagerRepository { }
