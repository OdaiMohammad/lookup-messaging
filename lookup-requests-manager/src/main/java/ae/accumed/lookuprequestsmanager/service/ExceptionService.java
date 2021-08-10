package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.ExceptionDTO;
import ae.accumed.lookuprequestsmanager.entities.Exceptions;
import ae.accumed.lookuprequestsmanager.repository.ExceptionRepository;
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
public class ExceptionService {
    private final ExceptionRepository exceptionRepository;
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final HashMap<Integer, String> columnIndexMapping = new HashMap<Integer, String>() {
        {
            put(0, "id");
            put(1, "exceptionDate");
            put(2, "exceptionMessage");
            put(3, "emiratesId");
            put(4, "facility");
        }
    };

    @Autowired
    public ExceptionService(ExceptionRepository exceptionRepository) {
        this.exceptionRepository = exceptionRepository;
    }

    public ExceptionDTO findById(int id) {
        Optional<Exceptions> exceptionsOptional = exceptionRepository.findById(id);
        if (exceptionsOptional.isPresent()) {
            Exceptions exception = exceptionsOptional.get();
            return new ExceptionDTO(exception.getId(),
                    exception.getExceptionDate().format(dateTimeFormatter),
                    exception.getExceptionTrace(),
                    exception.getExceptionMessage(),
                    exception.getEmiratesId(),
                    exception.getFacility(),
                    exception.getTransactionId());
        }
        return null;
    }

    public ArrayList<ExceptionDTO> findAll(
            int start,
            int length,
            int sortColumn,
            String sortDirection,
            String query) {
        Sort sort = getSortForColumn(sortColumn, sortDirection);
        Pageable pageable = PageRequest.of(start / length, length, sort);
        Page<Exceptions> exceptions = exceptionRepository.findAll(getSpecification(query), pageable);
        return (ArrayList<ExceptionDTO>) exceptions.stream()
                .map(exception ->
                        new ExceptionDTO(
                                exception.getId(),
                                exception.getExceptionDate().format(dateTimeFormatter),
                                exception.getExceptionTrace(),
                                exception.getExceptionMessage(),
                                exception.getEmiratesId(),
                                exception.getFacility(),
                                exception.getTransactionId()
                        ))
                .collect(Collectors.toList());
    }

    public int count(String query) {
        return (int) exceptionRepository.count(getSpecification(query));
    }

    private Sort getSortForColumn(int sortColumn, String sortDirection) {
        return sortDirection.equals("asc") ?
                Sort.by(columnIndexMapping.get(sortColumn)).ascending() :
                Sort.by(columnIndexMapping.get(sortColumn)).descending();

    }

    private Specification<Exceptions> getSpecification(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);

            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("id").as(String.class), "%" + query + "%"),
                    criteriaBuilder.like(root.get("exceptionDate").as(String.class), "%" + query + "%"),
                    criteriaBuilder.like(root.get("exceptionTrace"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("exceptionMessage"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("emiratesId"), "%" + query + "%"),
                    criteriaBuilder.like(root.get("facility"), "%" + query + "%")
            );

            return criteriaBuilder.and(predicate);
        };
    }
}
