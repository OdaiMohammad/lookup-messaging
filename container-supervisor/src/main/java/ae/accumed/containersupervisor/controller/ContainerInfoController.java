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
}
