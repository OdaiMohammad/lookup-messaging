package ae.accumed.lookuprequestsdistributor;

import ae.accumed.lookuprequestsdistributor.services.LookupRequestDistributionService;
import ae.accumed.lookuprequestsdistributor.services.TopicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class LookupRequestDistributor {
    private static Logger logger = LoggerFactory.getLogger(LookupRequestDistributor.class);

    private Set<String> topics;
    private final TopicsService topicsService;
    private final LookupRequestDistributionService lookupRequestDistributionService;

    @Autowired
    public LookupRequestDistributor(TopicsService topicsService, LookupRequestDistributionService lookupRequestDistributionService) {
        this.topicsService = topicsService;
        this.lookupRequestDistributionService = lookupRequestDistributionService;
        topics = new HashSet<>();
        topics.addAll(topicsService.listTopics());
    }

    @KafkaListener(id = "distribution-consumer", topics = "mssql-dev.dbo.TRANSACTIONS")
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
