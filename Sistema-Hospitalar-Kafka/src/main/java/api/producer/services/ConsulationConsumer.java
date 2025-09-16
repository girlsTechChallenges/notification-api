package api.producer.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsulationConsumer {
    @KafkaListener(topics = "consulations", groupId = "group-consulation")
    public void consume(String mensagem) {
        System.out.println("Mensagem recebida do Kafka: " + mensagem);
    }
}
