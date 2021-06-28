package ae.accumed.lookuprequestsdistributor.services;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class TopicsService {
    private static Logger logger = LoggerFactory.getLogger(LookupRequestDistributionService.class);

    private final KafkaAdmin kafkaAdmin;

    @Value("${new.topic.default.partitions}")
    private int partitions;

    @Value("${new.topic.default.replicas}")
    private int replicas;

    @Autowired
    public TopicsService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public Set<String> listTopics() {
        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());

            ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
            listTopicsOptions.listInternal(true);
            return adminClient.listTopics().names().get();
        } catch (Exception e) {
            logger.error("Error getting list of topics", e);
            return new HashSet<>();
        }
    }

    public void createTopic(String topicName) {
        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
            NewTopic newTopic = TopicBuilder.name(topicName).partitions(partitions).replicas(replicas).build();
            CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
            for (Map.Entry<String, KafkaFuture<Void>> entry : createTopicsResult.values().entrySet()) {
                try {
                    entry.getValue().get();
                    logger.info("topic {} created", entry.getKey());
                } catch (TopicExistsException e) {
                    logger.info(String.format("Topic %s already exists", topicName));
                }
            }
        } catch (Exception e) {
            logger.info(String.format("Error creating topic %s", topicName), e);
        }
    }
}
