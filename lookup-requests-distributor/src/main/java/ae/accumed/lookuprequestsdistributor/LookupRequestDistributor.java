package ae.accumed.lookuprequestsdistributor;

import ae.accumed.lookuprequestsdistributor.services.LookupRequestDistributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class LookupRequestDistributor {
    private static Logger logger = LoggerFactory.getLogger(LookupRequestDistributor.class);

    private final LookupRequestDistributionService lookupRequestDistributionService;

    @Autowired
    public LookupRequestDistributor(LookupRequestDistributionService lookupRequestDistributionService) {
        this.lookupRequestDistributionService = lookupRequestDistributionService;
    }

    @KafkaListener(id = "distribution-consumer", topics = "${topic.lookup.requests.name}")
    private void onLookupRequest(String message, Acknowledgment acknowledgment) {
        logger.info("Received message: {}", message);
        try {
            lookupRequestDistributionService.distributeMessage(message);
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
        acknowledgment.acknowledge();
    }
}
