package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.service.ContainerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/stop/{containerId}")
    public String stopContainer(@PathVariable String containerId, Model model) {
        containerInfoService.stopContainer(containerId);
        model.addAttribute("data", containerInfoService.getContainerInfo("lookup"));
        return "redirect:/service";
    }

    @PostMapping("/restart/{containerId}")
    public String restartContainer(@PathVariable String containerId, Model model) {
        containerInfoService.restartContainer(containerId);
        model.addAttribute("data", containerInfoService.getContainerInfo("lookup"));
        return "redirect:/service";
    }

    @PostMapping("/start/{containerId}")
    public String startContainer(@PathVariable String containerId, Model model) {
        containerInfoService.startContainer(containerId);
        model.addAttribute("data", containerInfoService.getContainerInfo("lookup"));
        return "redirect:/service";
    }
}
