package api.service;

import api.domain.model.Consult;
import api.domain.model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        Patient pacient = new Patient("Jorginho", "jorginho@gmail.com");
        Consult consult = new Consult();
        consult.setId("1");
        consult.setPacient(pacient);

        consultService.processConsult(consult);

        BlockingQueue<Consult> queue = consultService.getEmailQueue();
        assertEquals(1, queue.size());
        assertEquals("1", queue.peek().getId());
    }

    @Test
    void consume_shouldProcessMessageAndAck() throws Exception {
        Patient pacient = new Patient("Jorginho", "jorginho@gmail.com");
        Consult consult = new Consult();
        consult.setId("1");
        consult.setPacient(pacient);

        String message = objectMapper.writeValueAsString(consult);

        Acknowledgment ack = mock(Acknowledgment.class);

        consultService.consume(message, ack);

        BlockingQueue<Consult> queue = consultService.getEmailQueue();
        assertEquals(1, queue.size());
        assertEquals("1", queue.peek().getId());

        verify(ack, times(1)).acknowledge();
    }

    @Test
    void consume_shouldHandleInvalidMessage() {
        String invalidMessage = "invalid json";
        Acknowledgment ack = mock(Acknowledgment.class);

        assertDoesNotThrow(() -> consultService.consume(invalidMessage, ack));

        assertTrue(consultService.getEmailQueue().isEmpty());
        verify(ack, never()).acknowledge();
    }
}
