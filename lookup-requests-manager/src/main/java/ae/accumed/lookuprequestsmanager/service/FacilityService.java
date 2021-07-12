package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public Iterable<Facility> findAll() {
        return facilityRepository.findAll();
    }

    public Facility findById(int id) {
        Optional<Facility> payerOptional = facilityRepository.findById(id);
        return payerOptional.orElse(null);
    }
}
