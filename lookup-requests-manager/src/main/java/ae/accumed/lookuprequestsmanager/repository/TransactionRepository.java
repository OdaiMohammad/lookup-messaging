package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> , JpaSpecificationExecutor<Transactions> {
}
