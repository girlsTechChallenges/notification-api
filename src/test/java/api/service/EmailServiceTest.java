package api.service;

import api.domain.model.Consult;
import api.domain.model.Patient;
import api.exception.EmailNotSentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }

    private Consult createConsult(String status) {
        Patient pacient = new Patient("Jorginho", "jorginho@gmail.com");
        Consult consult = new Consult();
        consult.setId("1");
        consult.setNameProfessional("Dra. Maria Silva");
        consult.setPacient(pacient);
        consult.setLocalTime("14:30:00");
        consult.setDate("2025-10-03");
        consult.setReason("Consulta de rotina");
        consult.setStatusConsult(status);
        return consult;
    }

    @Test
    void shouldSendEmailSuccessfullyForScheduled() {
        Consult consult = createConsult("SCHEDULED");

        emailService.sendEmail(consult);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals("jorginho@gmail.com", sentMessage.getTo()[0]);
        assertEquals("Agendamento da Consulta", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("AGENDADA"));
        assertTrue(sentMessage.getText().contains("Dra. Maria Silva"));
    }

    @Test
    void shouldSendEmailSuccessfullyForCarriedOut() {
        Consult consult = createConsult("CARRIED_OUT");

        emailService.sendEmail(consult);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals("Realização da Consulta", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("REALIZADA"));
    }

    @Test
    void shouldSendEmailSuccessfullyForCancelled() {
        Consult consult = createConsult("CANCELLED");

        emailService.sendEmail(consult);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals("Cancelamento da Consulta", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("CANCELADA"));
    }

    @Test
    void shouldThrowEmailNotSentExceptionWhenMailFails() {
        Consult consult = createConsult("SCHEDULED");

        doThrow(new MailException("Falha no envio") {}).when(mailSender).send(any(SimpleMailMessage.class));

        EmailNotSentException exception = assertThrows(EmailNotSentException.class, () -> {
            emailService.sendEmail(consult);
        });

        assertEquals("Não foi possível enviar o email, tente novamente mais tarde.", exception.getMessage());
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
