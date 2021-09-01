package ae.accumed.containersupervisor.controller;

import ae.accumed.containersupervisor.service.ContainerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/containers")
public class ContainerInfoController {

    private final ContainerInfoService containerInfoService;

    @Autowired
    public ContainerInfoController(ContainerInfoService containerInfoService) {
        this.containerInfoService = containerInfoService;
    }

    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(defaultValue = "") String name) {
        return new ResponseEntity<>(containerInfoService.getContainersInfoByName(name), HttpStatus.OK);
    }

    @PostMapping("/restart/{containerId}")
    public ResponseEntity<Object> restart(@PathVariable String containerId) {
        containerInfoService.restartContainer(containerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/start/{containerId}")
    public ResponseEntity<Object> start(@PathVariable String containerId) {
        containerInfoService.startContainer(containerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/stop/{containerId}")
    public ResponseEntity<Object> stop(@PathVariable String containerId) {
        containerInfoService.stopContainer(containerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
