package app.projetaria.pockafka.consumer;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${spring.kafka.topics.test}")
public class KafkaConsumer {

    @KafkaHandler
    public void listen(@Payload String message,
                       @Header("EXAMPLE-HEADER1") String data
    ) {
        System.out.println("CONSUMER " + data);
        System.out.println(message);
    }
}
