package trucking.repository;

import org.springframework.data.repository.CrudRepository;
import trucking.model.Driver;
import trucking.model.Offer;
import trucking.model.Order;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer, Long>  {

    List<Offer> findAllByOrder(Order order);
    List<Offer> findAllByDriver(Driver driver);

}
