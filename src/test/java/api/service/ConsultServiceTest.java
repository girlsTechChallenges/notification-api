//package api.service;
//
//import api.domain.model.Consult;
//import api.domain.model.Patient;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class ConsultServiceTest {
//
//    private KafkaTemplate kafkaTemplate;
//    private ObjectMapper objectMapper;
//    private EmailService emailService;
//    private ConsultService consultService;
//
//    @BeforeEach
//    void setUp() {
//        kafkaTemplate = mock(KafkaTemplate.class);
//        objectMapper = mock(ObjectMapper.class);
//        emailService = mock(EmailService.class);
//
//        consultService = new ConsultService(null,kafkaTemplate, objectMapper, emailService);
//        consultService.topic = "test-topic"; // definindo topic para o teste
//    }
//
//    private Consult createConsult() {
//        Patient pacient = new Patient("Jorginho", "jorginho@gmail.com");
//        Consult consult = new Consult();
//        consult.setId("1");
//        consult.setNameProfessional("Dra. Maria Silva");
//        consult.setPacient(pacient);
//        consult.setLocalTime("14:30:00");
//        consult.setDate("2025-10-03");
//        consult.setReason("Consulta de rotina");
//        consult.setStatusConsult("SCHEDULED");
//        return consult;
//    }
//
//    @Test
//    void shouldSendConsultMessageToKafka() throws JsonProcessingException {
//        Consult consult = createConsult();
//        String mensagemJson = "{\"id\":\"1\"}";
//        when(objectMapper.writeValueAsString(consult)).thenReturn(mensagemJson);
//
//        consultService.sendConsult(consult);
//
//        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
//        verify(kafkaTemplate, times(1)).send(eq("test-topic"), messageCaptor.capture());
//
//        assertEquals(mensagemJson, messageCaptor.getValue());
//        verify(objectMapper, times(1)).writeValueAsString(consult);
//    }
//
//    @Test
//    void shouldProcessConsultBySendingToKafkaAndSendingEmail() throws JsonProcessingException {
//        Consult consult = createConsult();
//        String mensagemJson = "{\"id\":\"1\"}";
//        when(objectMapper.writeValueAsString(consult)).thenReturn(mensagemJson);
//
//        consultService.processConsult(consult);
//
//        verify(kafkaTemplate).send("test-topic", mensagemJson);
//        verify(emailService).sendEmail(consult);
//    }
//
//    @Test
//    void shouldThrowJsonProcessingExceptionWhenSendConsultFails() throws JsonProcessingException {
//        Consult consult = createConsult();
//        when(objectMapper.writeValueAsString(consult)).thenThrow(new JsonProcessingException("Erro") {});
//
//        org.junit.jupiter.api.Assertions.assertThrows(JsonProcessingException.class, () -> consultService.sendConsult(consult));
//
//        verify(kafkaTemplate, never()).send(anyString(), anyString());
//        verify(emailService, never()).sendEmail(any());
//    }
//}
//
