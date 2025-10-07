package api.service;

import api.domain.model.Consult;
import api.domain.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailSchedulerTest {

    private ConsultService consultService;
    private EmailService emailService;
    private EmailScheduler emailScheduler;

    private BlockingQueue<Consult> emailQueue;

    @BeforeEach
    void setUp() {
        emailQueue = new LinkedBlockingQueue<>();
        consultService = mock(ConsultService.class);
        emailService = mock(EmailService.class);

        when(consultService.getEmailQueue()).thenReturn(emailQueue);

        emailScheduler = new EmailScheduler(consultService, emailService);
    }

    @Test
    void processEmails_shouldSendEmailsAndClearQueue() {
        // Arrange
        Consult consult1 = new Consult();
        consult1.setId("1");
        consult1.setPacient(new Patient("Jorginho", "jorginho@gmail.com"));

        Consult consult2 = new Consult();
        consult2.setId("2");
        consult2.setPacient(new Patient("Maria", "maria@gmail.com"));

        emailQueue.add(consult1);
        emailQueue.add(consult2);

        // Act
        emailScheduler.processEmails();

        // Assert
        verify(emailService, times(1)).sendEmail(consult1);
        verify(emailService, times(1)).sendEmail(consult2);
        assertEquals(0, emailQueue.size());
    }

    @Test
    void processEmails_shouldDoNothingWhenQueueIsEmpty() {
        // Act
        emailScheduler.processEmails();

        // Assert
        verify(emailService, never()).sendEmail(any());
        assertEquals(0, emailQueue.size());
    }

    @Test
    void processEmails_shouldReaddConsultWhenEmailFails() {
        // Arrange
        Consult consult = new Consult();
        consult.setId("1");
        consult.setPacient(new Patient("Jorginho", "jorginho@gmail.com"));

        emailQueue.add(consult);

        doThrow(new RuntimeException("Falha no envio"))
                .when(emailService).sendEmail(consult);

        // Act
        emailScheduler.processEmails();

        // Assert
        verify(emailService, times(1)).sendEmail(consult);
        // Consulta deve ser reinserida na fila
        assertEquals(1, emailQueue.size());
        assertEquals("1", emailQueue.peek().getId());
    }
}
