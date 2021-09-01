package ae.accumed.lookuprequestsdistributor.services;

import ae.accumed.lookuprequestsdistributor.entities.Account;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LookupRequestDistributionService {
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

    public void distributeMessage(JsonNode transaction) {
        int accountId = transaction.get("account_id").asInt();
        String accountTopicName = createTopicOrIncreasePartitionsForAccountIfNecessary(accountId);
        if (accountTopicName != null) {
            topics.add(accountTopicName);
            sendMessage(accountTopicName, transaction);
        }
        String topicName = createTopicOrIncreasePartitionsIfNecessary(accountId);
        if (topicName != null) {
            topics.add(topicName);
            sendMessage(topicName, transaction);
        }
    }

    @Deprecated
    private String createTopicOrIncreasePartitionsForAccountIfNecessary(int accountId) {
        ArrayList<Integer> accounts = (ArrayList<Integer>) topics
                .stream()
                .filter(topic -> Character.isDigit(topic.toCharArray()[topic.length() - 1]))
                .map(topic -> Integer.parseInt(topic.split("-")[topic.split("-").length - 1]))
                .collect(Collectors.toList());
        Account account = accountService.findById(accountId);
        if (account != null) {
            String topicName = String.format("%s-%s-%s", account.getPayersByPayerId().getPayerCode(), account.getFacilityByFacilityId().getFacilityCode(), accountId);
            if (!accounts.contains(accountId)) {
                topicsService.createTopic(topicName);
            }
            topicsService.increasePartitionsIfNecessary(topicName);
            return topicName;
        }
        return null;
    }

    private String createTopicOrIncreasePartitionsIfNecessary(int accountId) {
        Account account = accountService.findById(accountId);
        if (account != null) {
            String topicName = String.format("%s", account.getPayersByPayerId().getPayerCode());
            if (!topics.contains(topicName))
                topicsService.createTopic(topicName);
            topicsService.increasePartitionsIfNecessary(topicName);
            return topicName;
        }
        return null;
    }

    public void sendMessage(String topic, JsonNode payload) {
        kafkaTemplate.send(topic, payload.get("id").asText(), payload);
    }
}
