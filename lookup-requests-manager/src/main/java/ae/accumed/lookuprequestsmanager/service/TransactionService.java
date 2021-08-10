package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.TransactionDTO;
import ae.accumed.lookuprequestsmanager.dto.TransactionDetailsDTO;
import ae.accumed.lookuprequestsmanager.entities.Transactions;
import ae.accumed.lookuprequestsmanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final HashMap<Integer, String> columnIndexMapping = new HashMap<Integer, String>() {
        {
            put(0, "id");
            put(1, "bulkId");
            put(2, "createDate");
            put(3, "eid");
            put(4, "result");
            put(5, "resultDate");
            put(6, "source");
            put(7, "status");
        }
    };

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDetailsDTO findById(int id) {
        Optional<Transactions> transactionsOptional = transactionRepository.findById(id);
        if (transactionsOptional.isPresent()) {
            Transactions transaction = transactionsOptional.get();
            return new TransactionDetailsDTO(transaction.getId(),
                    transaction.getBulkId(),
                    transaction.getCreateDate().format(dateFormat),
                    transaction.getEid(),
                    transaction.getResult(),
                    transaction.getResultDate() != null ? transaction.getResultDate().format(dateFormat) : null,
                    transaction.getSource(),
                    transaction.getStatus(),
                    transaction.getHtml());
        }
        return null;
    }

    public ArrayList<TransactionDTO> findAll(
            int start,
            int length,
            int sortColumn,
            String sortDirection,
            String query) {
        Sort sort = getSortForColumn(sortColumn, sortDirection);
        Pageable pageable = PageRequest.of(start / length, length, sort);
        Page<Transactions> transactions = transactionRepository.findAll(getSpecification(query), pageable);
        return (ArrayList<TransactionDTO>) transactions.stream()
                .map(transaction ->
                        new TransactionDTO(
                                transaction.getId(),
                                transaction.getBulkId(),
                                transaction.getCreateDate().format(dateFormat),
                                transaction.getEid(),
                                transaction.getSource(),
                                transaction.getStatus()
                        ))
                .collect(Collectors.toList());
    }

    private Sort getSortForColumn(int sortColumn, String sortDirection) {
        return sortDirection.equals("asc") ?
                Sort.by(columnIndexMapping.get(sortColumn)).ascending() :
                Sort.by(columnIndexMapping.get(sortColumn)).descending();

    }

    public int count(String query) {
        return (int) transactionRepository.count(getSpecification(query));
    }

    private Specification<Transactions> getSpecification(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);

            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("id").as(String.class), "%" + query + "%"),
                    criteriaBuilder.like(root.get("bulkId").as(String.class), "%" + query + "%"),
                    criteriaBuilder.like(root.get("createDate").as(String.class), "%" + query + "%"),
                    criteriaBuilder.like(root.get("eid"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("html"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("result"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("resultDate").as(String.class), "%" + query + "%"),
                    criteriaBuilder.like(root.get("source"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("status"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("errorMessage"), "%" + query + "%")
            );

            return criteriaBuilder.and(predicate);
        };
    }
}
