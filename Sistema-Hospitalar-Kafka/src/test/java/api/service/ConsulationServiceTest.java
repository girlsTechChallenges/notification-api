package api.service;

import api.domain.model.Consulation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ConsulationServiceTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    private EmailService emailService;
    private ConsulationService consulationService;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        emailService = mock(EmailService.class);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        consulationService = new ConsulationService(kafkaTemplate, objectMapper, emailService);
        consulationService.topic = "consulations-topic";
    }

    @Test
    void sendConsulation_ShouldSendMessageToKafka() throws JsonProcessingException {
        Consulation consulation = new Consulation();
        consulation.setId(1L);
        consulation.setNomePaciente("Isabella");
        consulation.setNomeProfissional("Dr. Silva");
        consulation.setDataHora(LocalDateTime.parse("2025-09-21T20:00"));
        consulation.setMotivo("Consulta de rotina");

        consulationService.sendConsulation(consulation);

        // Captura a mensagem enviada para Kafka
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("consulations-topic"), messageCaptor.capture());

        String sentMessage = messageCaptor.getValue();
        assertThat(sentMessage)
                .contains("Isabella")
                .contains("Dr. Silva")
                .contains("Consulta de rotina");
    }

    @Test
    void processConsultation_ShouldSendMessageToKafkaAndSendEmail() throws JsonProcessingException {
        Consulation consulation = new Consulation();
        consulation.setId(1L);
        consulation.setNomePaciente("Isabella");
        consulation.setNomeProfissional("Dr. Silva");
        consulation.setDataHora(LocalDateTime.parse("2025-09-21T20:00"));
        consulation.setMotivo("Consulta de rotina");

        consulationService.processConsultation(consulation);

        // Verifica envio para Kafka
        verify(kafkaTemplate, times(1)).send(eq("consulations-topic"), anyString());

        // Verifica envio de email
        verify(emailService, times(1)).sendEmail(consulation);
    }
}

