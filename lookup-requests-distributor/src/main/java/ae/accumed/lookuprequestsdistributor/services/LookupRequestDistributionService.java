package ae.accumed.lookuprequestsdistributor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LookupRequestDistributionService {
    private static Logger logger = LoggerFactory.getLogger(LookupRequestDistributionService.class);


    private Set<String> topics;
    private final TopicsService topicsService;
    private final KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Autowired
    public LookupRequestDistributionService(TopicsService topicsService, KafkaTemplate<String, JsonNode> kafkaTemplate) {
        this.topicsService = topicsService;
        this.kafkaTemplate = kafkaTemplate;
        topics = new HashSet<>();
        topics.addAll(topicsService.listTopics());
    }

    public void distributeMessage(String messageJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode message = mapper.readTree(messageJson);
        if (message.get("payload").get("before") instanceof NullNode) {
            JsonNode transaction = message.get("payload").get("after");
            int accountId = transaction.get("ACCOUNT_ID").asInt();
            String payerLicence = transaction.get("PAYER_LICENCE").asText();
            String facilityLicence = transaction.get("FACILITY_LICENCE").asText();
            String topicName = String.format("%s-%s-%s", payerLicence, facilityLicence, accountId);
            if (!topics.contains(topicName)) {
                topicsService.createTopic(topicName);
            }
            sendMessage(topicName, transaction);
        } else {
            logger.error("Invalid message");
        }

    }

    public void sendMessage(String topic, JsonNode payload) {
        kafkaTemplate.send(topic, payload.get("ID").asText(),payload);
    }
}
