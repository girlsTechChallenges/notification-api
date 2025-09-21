package api.service;

import api.domain.model.Consulation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsulationService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Value("${app.kafka.topic.consulations}")
    private String topic;

    public ConsulationService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper, EmailService emailService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    public void sendConsulation(Consulation dto) throws JsonProcessingException {
        String mensagem = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, mensagem);
    }

    public void processConsultation(Consulation dto) throws JsonProcessingException {
        sendConsulation(dto);
        emailService.sendEmail(dto);
    }
}
