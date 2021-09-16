package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.dto.statistics.RequestsPerPayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer>, JpaSpecificationExecutor<Transactions> {

    @Query("select new ae.accumed.lookuprequestsmanager.dto.statistics.RequestsPerPayerDTO(p.payerCode, t.status, count(t)) from Transactions t join Account a on t.accountByAccountId = a join Payers p on a.payersByPayerId = p where t.createDate >= ?1 and t.createDate < ?2 group by p.payerCode, t.status")
    List<RequestsPerPayerDTO> getRequestsPerPayerStatistics(Date start, Date end);
}
