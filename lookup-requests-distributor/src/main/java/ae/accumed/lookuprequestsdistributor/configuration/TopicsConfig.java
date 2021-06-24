package ae.accumed.lookuprequestsdistributor.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicsConfig {

    @Value("${topic.lookup.requests.name}")
    private String lookupRequestsTopicName;

    @Value("${topic.lookup.requests.partitions}")
    private int lookupRequestsTopicPartitions;

    @Value("${topic.lookup.requests.replicas}")
    private int lookupRequestsTopicReplicas;

    @Bean
    public NewTopic lookupRequestsTopic() {
        return TopicBuilder.name(lookupRequestsTopicName)
                .partitions(lookupRequestsTopicPartitions)
                .replicas(lookupRequestsTopicReplicas)
                .build();
    }
}
