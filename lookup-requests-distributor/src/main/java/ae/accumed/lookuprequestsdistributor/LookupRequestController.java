package ae.accumed.lookuprequestsdistributor;

import ae.accumed.lookuprequestsdistributor.services.LookupRequestDistributionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void transaction(@RequestBody JsonNode transaction) {
        lookupRequestDistributionService.distributeMessage(transaction);
    }
}
