package api.service;

import api.controller.ConsulationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsulationConsumer {
    private final Logger logger = LoggerFactory.getLogger(ConsulationConsumer.class);

    @KafkaListener(topics = "${app.kafka.topics.consulations}", groupId = "${app.kafka.groupid}")
    public void consume(String mensagem) {
        logger.info("Mensagem recebida do Kafka: " + mensagem);
    }
}
