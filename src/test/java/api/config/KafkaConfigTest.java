package api.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConfigTest {

    private final KafkaConfig kafkaConfig = new KafkaConfig();

    @Test
    void consumerFactory_shouldContainExpectedProperties() {
        ConsumerFactory<String, String> consumerFactory = kafkaConfig.consumerFactory();

        assertNotNull(consumerFactory);

        var props = ((org.springframework.kafka.core.DefaultKafkaConsumerFactory<String, String>) consumerFactory).getConfigurationProperties();
        assertEquals("localhost:9092", props.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals("my-group", props.get(ConsumerConfig.GROUP_ID_CONFIG));
        assertEquals(StringDeserializer.class, props.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        assertEquals(StringDeserializer.class, props.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
        assertEquals(false, props.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG));
    }

    @Test
    void kafkaListenerContainerFactoryManualAck_shouldHaveManualAckMode() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                kafkaConfig.kafkaListenerContainerFactoryManualAck();

        assertNotNull(factory);
        assertNotNull(factory.getContainerProperties());

        assertEquals(
                org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL,
                factory.getContainerProperties().getAckMode()
        );
    }
}
