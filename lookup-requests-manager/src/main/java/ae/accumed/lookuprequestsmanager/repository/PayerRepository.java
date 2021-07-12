package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.entities.Payers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerRepository extends CrudRepository<Payers, Integer> {
}
