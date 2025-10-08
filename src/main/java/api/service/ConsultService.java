package api.service;

import api.domain.model.Consult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ConsultService {

    private static final Logger logger = LoggerFactory.getLogger(ConsultService.class);
    private final ObjectMapper objectMapper;

    @Getter
    private final BlockingQueue<Consult> emailQueue = new LinkedBlockingQueue<>(1000);

    public ConsultService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.consults}",
            groupId = "${app.kafka.groupid}",
            containerFactory = "kafkaListenerContainerFactoryManualAck"
    )
    public void consume(String mensagem, Acknowledgment ack) {
        try {
            Consult consult = objectMapper.readValue(mensagem, Consult.class);
            getEmailQueue().add(consult);
            logger.info("Mensagem recebida do Kafka: {}", mensagem);

            ack.acknowledge();
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem do Kafka: {}", e.getMessage(), e);
        }
    }

    public void processConsult(Consult consult) {
        emailQueue.add(consult);
        logger.info("Consulta adicionada à fila de envio de emails: {} - {}", consult.getId(), consult.getPatient().getEmail());
    }
}
