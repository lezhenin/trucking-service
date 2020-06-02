package trucking.core.repository.list;

import trucking.core.model.Manager;
import trucking.core.repository.ManagerRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class ManagerListRepository extends CrudListRepository<Manager> implements ManagerRepository { }
