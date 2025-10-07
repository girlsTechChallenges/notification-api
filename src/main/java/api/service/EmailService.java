package api.service;

import api.domain.model.Consult;
import api.enums.ConsultEmailStatus;
import api.enums.ConsultStatus;
import api.exception.EmailNotSentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private SimpleMailMessage chooseMessageEmail(Consult consult) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(consult.getPacient().getEmail());

        ConsultEmailStatus consultStatus;
        ConsultStatus status = ConsultStatus.valueOf(consult.getStatusConsult());

        if (ConsultStatus.SCHEDULED.equals(status)) {
            consultStatus = ConsultEmailStatus.AGENDADA;
            message.setSubject("Agendamento da Consulta");
        } else if (ConsultStatus.CARRIED_OUT.equals(status)) {
            consultStatus = ConsultEmailStatus.REALIZADA;
            message.setSubject("Realização da Consulta");
        } else {
            consultStatus = ConsultEmailStatus.CANCELADA;
            message.setSubject("Cancelamento da Consulta");
        }

        String emailBody = String.format(
                "Olá %s,%nSua consulta foi %s com %s.%n%nData: %s Hora: %s%nMotivo: %s%n%nAtenciosamente,%nSistema Hospitalar.",
                consult.getPacient().getName(),
                consultStatus,
                consult.getNameProfessional(),
                consult.getDate(),
                consult.getLocalTime(),
                consult.getReason()
        );

        message.setText(emailBody);
        return message;
    }

    public void sendEmail(Consult consult) {
        try {
            SimpleMailMessage message = chooseMessageEmail(consult);
            mailSender.send(message);
            logger.info("Email enviado com sucesso para: {} (id: {})", consult.getPacient().getEmail(), consult.getId());
        } catch (MailException e) {
            logger.error("Erro ao enviar email para {}: {}", consult.getPacient().getEmail(), e.getMessage());
            throw new EmailNotSentException("Não foi possível enviar o email, tente novamente mais tarde.");
        }
    }
}
