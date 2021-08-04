package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.entities.Exceptions;
import ae.accumed.lookuprequestsmanager.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionRepository extends JpaRepository<Exceptions, Integer> , JpaSpecificationExecutor<Exceptions> {
}
