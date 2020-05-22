package core.repository;

import core.model.Manager;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
    List<Manager> findAll();
}
