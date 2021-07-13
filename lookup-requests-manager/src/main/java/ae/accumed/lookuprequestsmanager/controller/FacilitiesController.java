package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/facility")
public class FacilitiesController {

    private final FacilityService facilityService;

    public FacilitiesController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public String facilities(Model model){
        model.addAttribute("data", facilityService.findAll());
        return "facilities";
    }

    @GetMapping("/new")
    public String newFacility(Model model) {
        model.addAttribute("facility", new FacilityDTO());
        return "new_facility";
    }

    @PostMapping("/new")
    public String createFacility(FacilityDTO facilityDTO, Model model) {
        facilityService.save(facilityDTO);
        model.addAttribute("data", facilityService.findAll());
        return "redirect:/facility";
    }
}
