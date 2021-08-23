package ae.accumed.lookuprequestsdistributor;

import ae.accumed.lookuprequestsdistributor.dto.TransactionDTO;
import ae.accumed.lookuprequestsdistributor.services.LookupRequestDistributionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class LookupRequestController {

    private final LookupRequestDistributionService lookupRequestDistributionService;

    @Autowired
    public LookupRequestController(LookupRequestDistributionService lookupRequestDistributionService) {
        this.lookupRequestDistributionService = lookupRequestDistributionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> transaction(@RequestBody TransactionDTO transaction) {
        lookupRequestDistributionService.distributeMessage(new ObjectMapper().valueToTree(transaction));
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
