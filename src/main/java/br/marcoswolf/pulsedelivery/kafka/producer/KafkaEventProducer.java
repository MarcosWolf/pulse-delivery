package br.marcoswolf.pulsedelivery.kafka.producer;

import br.marcoswolf.pulsedelivery.kafka.dto.OrderCreatedEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEventDTO event) {
        kafkaTemplate.send("order-created", event);
    }

    public void sendOrderUpdatedEvent(Object event) {
        kafkaTemplate.send("order-updated", event);
    }
}
