package ae.accumed.containersupervisor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    public ResponseEntity<Object> healthCheck(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
