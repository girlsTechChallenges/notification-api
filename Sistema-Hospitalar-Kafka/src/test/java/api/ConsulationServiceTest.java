package api;

import api.domain.model.Consulation;
import api.service.ConsulationService;
import api.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

class ConsulationServiceTest {

    @Test
    void testSendConsulation() throws JsonProcessingException {
        // Mock do KafkaTemplate
        KafkaTemplate<String, String> kafkaTemplate = Mockito.mock(KafkaTemplate.class);

        // Mock do EmailService
        EmailService emailService = Mockito.mock(EmailService.class);

        // ObjectMapper real
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Serviço com mocks
        ConsulationService service = new ConsulationService(kafkaTemplate, objectMapper, emailService);
        ReflectionTestUtils.setField(service, "topic", "teste");

        // DTO de teste
        Consulation dto = new Consulation();
        dto.setId(1L);
        dto.setNomePaciente("Isabella");
        dto.setNomeProfissional("Dr. Silva");
        dto.setDataHora("2025-04-12");
        dto.setMotivo("Consulta teste");
        dto.setStatus("AGENDADA");

        // Executa o método
        service.sendConsulation(dto);

        // Verifica se o KafkaTemplate.send foi chamado com o JSON do DTO
        Mockito.verify(kafkaTemplate).send(Mockito.anyString(), Mockito.anyString());
    }
}
