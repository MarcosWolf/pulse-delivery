package br.marcoswolf.pulsedelivery.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderCreatedTopic() {
        return new NewTopic("order-created", 1, (short) 1);
    }

    @Bean
    public NewTopic orderUpdatedTopic() {
        return new NewTopic("order-updated", 1, (short) 1);
    }
}
