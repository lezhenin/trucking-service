package trucking.core.repository.list;

import trucking.core.model.Client;
import trucking.core.repository.ClientRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class ClientListRepository extends CrudListRepository<Client> implements ClientRepository { }
