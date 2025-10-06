package api.service;

import api.domain.model.Consult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsultService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Value("${app.kafka.topic.consults}")
    String topic;

    public ConsultService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, EmailService emailService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    public void sendConsult(Consult dto) throws JsonProcessingException {
        String mensagem = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, mensagem);
    }

    public void processConsult(Consult dto) throws JsonProcessingException {
        sendConsult(dto);
        emailService.sendEmail(dto);
    }
}
