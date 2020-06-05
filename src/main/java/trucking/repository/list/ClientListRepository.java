package trucking.repository.list;

import trucking.model.Client;
import trucking.repository.ClientRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class ClientListRepository extends CrudListRepository<Client> implements ClientRepository { }
