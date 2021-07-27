package ae.accumed.lookuprequestsmanager.controller;

import ae.accumed.lookuprequestsmanager.dto.TransactionDTO;
import ae.accumed.lookuprequestsmanager.dto.TransactionDataTableDTO;
import ae.accumed.lookuprequestsmanager.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public String transactions() {
        return "transactions";
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Object> transaction(
            @RequestParam int draw,
            @RequestParam int start,
            @RequestParam int length,
            @RequestParam("order[0][column]") int sortColumn,
            @RequestParam("order[0][dir]") String sortDirection,
            @RequestParam("search[value]") String query) {
        ArrayList<TransactionDTO> transactionDTOs = transactionService.findAll(start, length, sortColumn, sortDirection, query);
        int count = transactionService.count(query);
        return new ResponseEntity<>(new TransactionDataTableDTO(draw, count, count, transactionDTOs), HttpStatus.OK);
    }
}
