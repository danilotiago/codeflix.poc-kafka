package app.projetaria.pockafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducer {

    @Value("${spring.kafka.topics.test}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafka;

    public void sendMessageSync(String message) {


        String key = "ANYKEY-" + UUID.randomUUID();

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, message + " " + LocalDateTime.now().toString());

        record.headers().add("EXAMPLE-HEADER1", "VALUE 1".getBytes(StandardCharsets.UTF_8));
        record.headers().add("EXAMPLE-HEADER2", "VALUE 2".getBytes(StandardCharsets.UTF_8));
        record.headers().add("EXAMPLE-HEADER3", "VALUE 3".getBytes(StandardCharsets.UTF_8));

        try {
            SendResult<String, String> sendResult = kafka.send(record).get();
            System.out.println("Sucesso ao postar, {topic}-{partition}@{offset}: " + sendResult.getRecordMetadata().toString());
        } catch (ExecutionException | InterruptedException ex) {
            System.out.println("Falha ao postar " + ex.getMessage());
        }
    }

    public void sendMessageAsync(String message) {

        String key = null;
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, message + " " + LocalDateTime.now().toString());

        kafka.send(record).addCallback(
            success -> System.out.println("Sucesso ao postar, {topic}-{partition}@{offset}: " + success.getRecordMetadata().toString()),
            err -> {
                System.out.println("Falha ao postar" + err.getMessage());
            });
    }
}
