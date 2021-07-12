package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
