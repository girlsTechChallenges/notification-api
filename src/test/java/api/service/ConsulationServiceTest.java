package api.service;

import api.domain.model.Consult;
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

class ConsultServiceTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    private EmailService emailService;
    private ConsultService consultService;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        emailService = mock(EmailService.class);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        consultService = new ConsultService(kafkaTemplate, objectMapper, emailService);
        consultService.topic = "consults-topic";
    }

//    @Test
//    void sendConsult_ShouldSendMessageToKafka() throws JsonProcessingException {
//        Consult consult = new Consult();
//        consult.setId(1L);
//        consult.setNameProfessional("Isabella");
//        consult.setNomeProfissional("Dr. Silva");
//        consult.setDataHora(LocalDateTime.parse("2025-09-21T20:00"));
//        consult.setReason("Consulta de rotina");
//
//        consultService.sendConsult(consult);
//
//        // Captura a mensagem enviada para Kafka
//        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
//        verify(kafkaTemplate, times(1)).send(eq("consults-topic"), messageCaptor.capture());
//
//        String sentMessage = messageCaptor.getValue();
//        assertThat(sentMessage)
//                .contains("Isabella")
//                .contains("Dr. Silva")
//                .contains("Consulta de rotina");
//    }

//    @Test
//    void processConsultation_ShouldSendMessageToKafkaAndSendEmail() throws JsonProcessingException {
//        Consult consult = new Consult();
//        consult.setId(1L);
//        consult.setNameProfessional("Isabella");
//        consult.setNomeProfissional("Dr. Silva");
//        consult.setDataHora(LocalDateTime.parse("2025-09-21T20:00"));
//        consult.setReason("Consulta de rotina");
//
//        consultService.processConsultation(consult);
//
//        // Verifica envio para Kafka
//        verify(kafkaTemplate, times(1)).send(eq("consults-topic"), anyString());
//
//        // Verifica envio de email
//        verify(emailService, times(1)).sendEmail(consult);
//    }
}

