package br.marcoswolf.pulsedelivery.kafka.consumer;

import br.marcoswolf.pulsedelivery.kafka.dto.OrderCreatedEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventConsumer {
    @KafkaListener(topics = "order-created", groupId = "pulsedelivery-group")
    public void consumeOrderCreated(OrderCreatedEventDTO event) {
        System.out.println("Received order created event: " + event);
    }

    @KafkaListener(topics = "order-updated", groupId = "pulsedelivery-group")
    public void consumeOrderUpdated(Object event) {
        System.out.println("Received order updated event: " + event);
    }
}
