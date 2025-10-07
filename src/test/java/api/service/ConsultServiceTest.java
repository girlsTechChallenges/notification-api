package api.service;

import api.domain.model.Consult;
import api.domain.model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.support.Acknowledgment;

import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultServiceTest {

    private ObjectMapper objectMapper;
    private ConsultService consultService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        consultService = new ConsultService(objectMapper);
    }

    @Test
    void processConsult_shouldAddConsultToQueue() {
        // Arrange
        Patient pacient = new Patient("Jorginho", "jorginho@gmail.com");
        Consult consult = new Consult();
        consult.setId("1");
        consult.setPacient(pacient);

        // Act
        consultService.processConsult(consult);

        // Assert
        BlockingQueue<Consult> queue = consultService.getEmailQueue();
        assertEquals(1, queue.size());
        assertEquals("1", queue.peek().getId());
    }

    @Test
    void consume_shouldProcessMessageAndAck() throws Exception {
        // Arrange
        Patient pacient = new Patient("Jorginho", "jorginho@gmail.com");
        Consult consult = new Consult();
        consult.setId("1");
        consult.setPacient(pacient);

        String message = objectMapper.writeValueAsString(consult);

        Acknowledgment ack = mock(Acknowledgment.class);

        // Act
        consultService.consume(message, ack);

        // Assert
        BlockingQueue<Consult> queue = consultService.getEmailQueue();
        assertEquals(1, queue.size());
        assertEquals("1", queue.peek().getId());

        verify(ack, times(1)).acknowledge();
    }

    @Test
    void consume_shouldHandleInvalidMessage() {
        // Arrange
        String invalidMessage = "invalid json";
        Acknowledgment ack = mock(Acknowledgment.class);

        // Act
        assertDoesNotThrow(() -> consultService.consume(invalidMessage, ack));

        // Assert: fila deve continuar vazia e ack não chamado
        assertTrue(consultService.getEmailQueue().isEmpty());
        verify(ack, never()).acknowledge();
    }
}
