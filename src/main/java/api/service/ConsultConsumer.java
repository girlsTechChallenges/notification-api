package api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public abstract class ConsultConsumer {
    private final Logger logger = LoggerFactory.getLogger(ConsultConsumer.class);

    @KafkaListener(topics = "${app.kafka.topics.consults}", groupId = "${app.kafka.groupid}")
    public void consume(String mensagem) {
        logger.info("Mensagem recebida do Kafka: {}", mensagem);
    }
}
