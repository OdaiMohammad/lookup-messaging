package ae.accumed.lookuprequestsdistributor.services;

import ae.accumed.lookuprequestsdistributor.entities.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LookupRequestDistributionService {
    private static Logger logger = LoggerFactory.getLogger(LookupRequestDistributionService.class);


    private Set<String> topics;
    private final TopicsService topicsService;
    private final AccountService accountService;
    private final KafkaTemplate<String, JsonNode> kafkaTemplate;

    @Autowired
    public LookupRequestDistributionService(TopicsService topicsService, AccountService accountService, KafkaTemplate<String, JsonNode> kafkaTemplate) {
        this.topicsService = topicsService;
        this.accountService = accountService;
        this.kafkaTemplate = kafkaTemplate;
        topics = new HashSet<>();
        topics.addAll(topicsService.listTopics());
    }

    public void distributeMessage(String messageJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode message = mapper.readTree(messageJson);
        if (message.get("payload").get("before") instanceof NullNode) {
            JsonNode transaction = message.get("payload").get("after");
            int accountId = transaction.get("account_id").asInt();
            String topicName = createTopicForAccountIfNotExists(accountId);
            if (topicName != null) {
                topics.add(topicName);
                sendMessage(topicName, transaction);
            }
        } else {
            logger.error("Invalid message");
            logger.error(message.toPrettyString());
        }

    }

    private String createTopicForAccountIfNotExists(int accountId) {
        ArrayList<Integer> accounts = (ArrayList<Integer>) topics
                .stream()
                .filter(topic -> topic.split("-").length == 3 && Character.isDigit(topic.length() - 1))
                .map(topic -> Integer.parseInt(topic.split("-")[topic.length()-1]))
                .collect(Collectors.toList());
        if (!accounts.contains(accountId)) {
            Account account = accountService.findById(accountId);
            if (account != null) {
                String topicName = String.format("%s-%s-%s", account.getPayersByPayerId().getPayerCode(), account.getFacilityByFacilityId().getFacilityCode(), accountId);
                topicsService.createTopic(topicName);
                return topicName;
            }
        }
        return null;
    }

    public void sendMessage(String topic, JsonNode payload) {
        kafkaTemplate.send(topic, payload.get("id").asText(), payload);
    }
}
