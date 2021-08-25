package ae.accumed.lookuprequestsdistributor.services;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

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
                    logger.info("Topic {} created", entry.getKey());
                } catch (TopicExistsException e) {
                    logger.info("Topic {} already exists, will increase partitions if necessary...", topicName);
                    Map<String, NewPartitions> newPartitionSet = new HashMap<>();
                    newPartitionSet.put(topicName, NewPartitions.increaseTo(partitions));
                    adminClient.createPartitions(newPartitionSet);
                    adminClient.close();
                }
            }
        } catch (Exception e) {
            logger.info(String.format("Error creating topic %s", topicName), e);
        }
    }

    @Async
    public void increasePartitionsIfNecessary(String topicName) {
        try {
            AtomicInteger topicCurrentPartitionsCount = new AtomicInteger();
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());

            adminClient.describeTopics(Collections.singletonList(topicName)).all().get().forEach((topic, description) -> {
                topicCurrentPartitionsCount.set(description.partitions().size());
            });

            if (topicCurrentPartitionsCount.get() != partitions) {
                Map<String, NewPartitions> newPartitionsMap = new HashMap<>();
                newPartitionsMap.put(topicName, NewPartitions.increaseTo(partitions));

                adminClient.createPartitions(newPartitionsMap).all().get();

                logger.info("Increased partitions of topic {} to {}", topicName, String.valueOf(partitions));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Failed to increase partitions for topic {}. Caused by: {}", topicName, e.getMessage());
        }
    }
}
