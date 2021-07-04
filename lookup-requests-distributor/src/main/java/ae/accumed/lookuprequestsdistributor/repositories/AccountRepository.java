package ae.accumed.lookuprequestsdistributor.repositories;

import ae.accumed.lookuprequestsdistributor.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
