package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.ExceptionDTO;
import ae.accumed.lookuprequestsmanager.dto.ExceptionDataTableDTO;
import ae.accumed.lookuprequestsmanager.service.ExceptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/exception")
public class ExceptionController {

    private final ExceptionService exceptionService;

    public ExceptionController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @GetMapping
    public String exceptions() {
        return "exceptions";
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Object> exception(
            @RequestParam int draw,
            @RequestParam int start,
            @RequestParam int length,
            @RequestParam("order[0][column]") int sortColumn,
            @RequestParam("order[0][dir]") String sortDirection,
            @RequestParam("search[value]") String query) {
        ArrayList<ExceptionDTO> exceptionDTOs = exceptionService.findAll(start, length, sortColumn, sortDirection, query);
        int count = exceptionService.count(query);
        return new ResponseEntity<>(new ExceptionDataTableDTO(draw, count, count, exceptionDTOs), HttpStatus.OK);
    }


    @GetMapping("/{exceptionId}")
    public String exceptionDetails(@PathVariable int exceptionId, Model model) {
        ExceptionDTO exception = exceptionService.findById(exceptionId);
        model.addAttribute("data", exception);
        return "exception_details";
    }
}
