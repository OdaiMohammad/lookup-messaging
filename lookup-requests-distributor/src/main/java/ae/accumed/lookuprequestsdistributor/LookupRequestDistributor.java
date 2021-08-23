package ae.accumed.lookuprequestsdistributor;

import ae.accumed.lookuprequestsdistributor.services.LookupRequestDistributionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
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
            ObjectMapper mapper = new ObjectMapper();
            JsonNode messageJson = mapper.readTree(message);
            if (messageJson.get("payload").get("before") instanceof NullNode) {
                lookupRequestDistributionService.distributeMessage(messageJson.get("payload").get("after"));
            } else {
                logger.error("Invalid message");
            }
        } catch (JsonProcessingException e) {
            logger.error("Error processing message", e);
        }
        acknowledgment.acknowledge();
    }
}
