package app.projetaria.pockafka.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.Map;

// EnableKafka -> if consumer class
@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.producer.enable.idempotence}")
    private String enableIdempotenceValue;

    @Value("${spring.kafka.producer.timeout.ms.request}")
    private String requestTimeoutMsValue;

    @Value("${spring.kafka.producer.timeout.ms.delivery}")
    private String deliveryTimeoutMsValue;

    /**
     * Producer bean:: factory
     *
     * @param kafkaProperties
     * @return
     */
    @Bean
    public ProducerFactory<String, String> producerFactory(final KafkaProperties kafkaProperties) {
        Map<String, Object> config = kafkaProperties.buildProducerProperties();
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotenceValue);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMsValue);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMsValue);

        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * producer bean:: using in producer classes
     *
     * @param producerFactory
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(final ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * consumer bean:: factory
     *
     * @param kafkaProperties
     * @return
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory(final KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    /**
     * consumer bean:: using in KafkaListener classes by annotation
     *
     * @param consumerFactory
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(final ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentListenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentListenerFactory.setConsumerFactory(consumerFactory);

        return concurrentListenerFactory;
    }
}
