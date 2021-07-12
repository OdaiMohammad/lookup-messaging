package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/facilities")
public class FacilitiesController {

    private final FacilityService facilityService;

    public FacilitiesController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public String facilities(Model model){
        ArrayList<Facility> facilities = (ArrayList<Facility>) facilityService.findAll();
        List<FacilityDTO> facilityDTOs = facilities.stream()
                .map(facility ->
                        new FacilityDTO(facility.getId(), facility.getDescription(), facility.getFacilityCode(), facility.getFacilityName()))
                .collect(Collectors.toList());
        model.addAttribute("data", facilityDTOs);
        return "facilities";
    }
}
