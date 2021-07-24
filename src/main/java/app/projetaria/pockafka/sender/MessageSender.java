package app.projetaria.pockafka.sender;

import app.projetaria.pockafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private KafkaProducer producer;

    @Scheduled(fixedDelay = 1000)
    public void sendMessage() {
        String message = "Uma mensagem de teste";

        producer.sendMessageSync(message);
    }
}
