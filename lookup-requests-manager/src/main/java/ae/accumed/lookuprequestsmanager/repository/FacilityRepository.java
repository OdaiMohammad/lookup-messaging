package ae.accumed.lookuprequestsmanager.repository;

import ae.accumed.lookuprequestsmanager.entities.Facility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends CrudRepository<Facility, Integer> {
}
