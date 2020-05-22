package core.repository.list;

import core.model.Manager;
import core.repository.ManagerRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class ManagerListRepository extends CrudListRepository<Manager> implements ManagerRepository { }
