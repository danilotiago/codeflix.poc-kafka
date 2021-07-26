package app.projetaria.pockafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.topics.test}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println(record.headers());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.offset());

        ack.acknowledge();
    }
}
