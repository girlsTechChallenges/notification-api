package api.service;

import api.domain.model.Consult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultService {

    private final Logger logger = LoggerFactory.getLogger(ConsultService.class);

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public ConsultService(ObjectMapper objectMapper, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${app.kafka.topics.consults}", groupId = "${app.kafka.groupid}")
    public void consume(String mensagem) {
        try {
            Consult consult = objectMapper.readValue(mensagem, Consult.class);

            logger.info("Mensagem recebida do Kafka: {}", mensagem);

            emailService.sendEmail(consult);
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem do Kafka: {}", e.getMessage(), e);
        }
    }

//    @KafkaListener(topics = "${app.kafka.topics.consults}", groupId = "${app.kafka.groupid}")
//    public void consume(String mensagem) {
//        logger.info("Mensagem recebida do Kafka: {}", mensagem);
//    }

    public void sendConsult(Consult dto) throws JsonProcessingException {
        String mensagem = objectMapper.writeValueAsString(dto);
        consume(mensagem);
    }

    public void processConsult(Consult dto) throws JsonProcessingException {
        sendConsult(dto);
        emailService.sendEmail(dto);
    }
}
