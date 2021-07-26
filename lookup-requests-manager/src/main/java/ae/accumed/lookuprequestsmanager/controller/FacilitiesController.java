package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.FacilityDTO;
import ae.accumed.lookuprequestsmanager.dto.PayerDTO;
import ae.accumed.lookuprequestsmanager.entities.Facility;
import ae.accumed.lookuprequestsmanager.entities.Payers;
import ae.accumed.lookuprequestsmanager.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @PostMapping("/activate/{facilityId}")
    public String activatePayer(@PathVariable int facilityId, Model model) {
        facilityService.activateFacility(facilityId);
        model.addAttribute("data", facilityService.findAll());
        return "redirect:/facility";
    }

    @PostMapping("/deactivate/{facilityId}")
    public String deactivatePayer(@PathVariable int facilityId, Model model) {
        facilityService.deactivateFacility(facilityId);
        model.addAttribute("data", facilityService.findAll());
        return "redirect:/facility";
    }

    @GetMapping("/new")
    public String newFacility(Model model) {
        model.addAttribute("facility", new FacilityDTO());
        return "new_facility";
    }

    @PostMapping("/new")
    public String createFacility(@Valid @ModelAttribute("facility") FacilityDTO facilityDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("facility", facilityDTO);
            return "new_facility";
        }
        facilityService.save(facilityDTO);
        model.addAttribute("data", facilityService.findAll());
        return "redirect:/facility";
    }
}
