package api.service;

import api.domain.model.Consult;
import api.enums.ConsultStatus;
import api.exception.EmailNotSentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }

//    private Consult createConsult(ConsultStatus status) {
//        Consult c = new Consult();
//        c.setNameProfessional("Isabella");
//        c.setNomeProfissional("Dr. Silva");
//        c.setDataHora(LocalDateTime.of(2025, 9, 21, 14, 30));
//        c.setReason("Consulta de rotina");
//        c.setEmailPaciente("isabella@example.com");
//        c.setStatusConsult(status);
//        return c;
//    }

//    @Test
//    void sendEmail_ShouldSendEmailForAgendada() {
//        Consult consult = createConsult(ConsultStatus.SCHEDULED);
//
//        emailService.sendEmail(consult);
//
//        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
//        verify(mailSender, times(1)).send(captor.capture());
//
//        SimpleMailMessage sentMessage = captor.getValue();
//        assertThat(sentMessage.getTo()).contains("isabella@example.com");
//        assertThat(sentMessage.getSubject()).isEqualTo("Confirmação de Consulta");
//        assertThat(sentMessage.getText()).contains("Sua consulta foi CONFIRMADA com Dr. Silva");
//    }
//
//    @Test
//    void sendEmail_ShouldSendEmailForCancelada() {
//        Consult consult = createConsult(ConsultStatus.CANCELLED);
//
//        emailService.sendEmail(consult);
//
//        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
//        verify(mailSender).send(captor.capture());
//
//        SimpleMailMessage sentMessage = captor.getValue();
//        assertThat(sentMessage.getSubject()).isEqualTo("Realização de Consulta");
//        assertThat(sentMessage.getText()).contains("Sua consulta foi REALIZADA com Dr. Silva");
//    }
//
//    @Test
//    void sendEmail_ShouldThrowExceptionWhenMailFails() {
//        Consult consult = createConsult(ConsultStatus.SCHEDULED);
//        doThrow(new org.springframework.mail.MailSendException("Falha no envio"))
//                .when(mailSender).send(any(SimpleMailMessage.class));
//
//        assertThrows(EmailNotSentException.class, () -> emailService.sendEmail(consult));
//    }
}

