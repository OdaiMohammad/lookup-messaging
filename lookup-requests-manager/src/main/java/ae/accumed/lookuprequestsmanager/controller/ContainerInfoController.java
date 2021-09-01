package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.service.ContainerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service")
public class ContainerInfoController {

    private final ContainerInfoService containerInfoService;

    @Autowired
    public ContainerInfoController(ContainerInfoService containerInfoService) {
        this.containerInfoService = containerInfoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("data", containerInfoService.getContainerInfo("lookup"));
        return "services";
    }
}
