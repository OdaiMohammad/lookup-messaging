package ae.accumed.lookuprequestsmanager.service;

import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public ArrayList<FacilityDTO> findAll() {
        ArrayList<Facility> facilities = (ArrayList<Facility>) facilityRepository.findAll();
        return (ArrayList<FacilityDTO>) facilities.stream()
                .map(facility ->
                        new FacilityDTO(facility.getId(), facility.getDescription(), facility.isFacilityActive(), facility.getFacilityCode(), facility.getFacilityName()))
                .collect(Collectors.toList());
    }

    public Facility findById(int id) {
        Optional<Facility> payerOptional = facilityRepository.findById(id);
        return payerOptional.orElse(null);
    }

    public FacilityDTO findByIdAsDTO(int id) {
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if(facilityOptional.isPresent()) {
            Facility facility = facilityOptional.get();
            return new FacilityDTO(facility.getId(), facility.getDescription(), facility.isFacilityActive(), facility.getFacilityCode(), facility.getFacilityName());
        }
        return null;

    }

    public void save(FacilityDTO facilityDTO) {
        Facility facility = new Facility();
        facility.setFacilityName(facilityDTO.getFacilityName());
        facility.setFacilityCode(facilityDTO.getFacilityCode());
        facility.setDescription(facilityDTO.getDescription());
        facilityRepository.save(facility);
    }

    public void activateFacility(int id){
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if(facilityOptional.isPresent()){
            Facility facility = facilityOptional.get();
            facility.setFacilityActive(true);
            facilityRepository.save(facility);
        }
    }

    public void deactivateFacility(int id){
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        if(facilityOptional.isPresent()){
            Facility facility = facilityOptional.get();
            facility.setFacilityActive(false);
            facilityRepository.save(facility);
        }
    }

    public void edit(Facility facility) {
        facilityRepository.save(facility);
    }
}
