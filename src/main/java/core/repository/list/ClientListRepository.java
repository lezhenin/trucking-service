package core.repository.list;

import core.model.Client;
import core.repository.ClientRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class ClientListRepository extends CrudListRepository<Client> implements ClientRepository { }
