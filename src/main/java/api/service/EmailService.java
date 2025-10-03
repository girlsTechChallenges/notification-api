package api.service;

import api.domain.model.Consulation;
import api.enums.ConsulationEmailStatus;
import api.enums.ConsulationStatus;
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

    private SimpleMailMessage chooseMessageEmail(Consulation consulation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(consulation.getPacient().getEmail());

        ConsulationEmailStatus consulationStatus;

        ConsulationStatus status = ConsulationStatus.valueOf(consulation.getStatusConsulation());

        if (ConsulationStatus.SCHEDULED.equals(status)) {
            consulationStatus = ConsulationEmailStatus.AGENDADA;
            message.setSubject("Agendamento da Consulta");
        } else if (ConsulationStatus.CARRIED_OUT.equals(status)) {
            consulationStatus = ConsulationEmailStatus.REALIZADA;
            message.setSubject("Realização da Consulta");
        } else {
            consulationStatus = ConsulationEmailStatus.CANCELADA;
            message.setSubject("Cancelamento da Consulta");
        }

        String emailBody = String.format(
                "Olá %s,%nSua consulta foi %s com %s.%n%nData: %s Hora: %s%nMotivo: %s%n%nAtenciosamente,%nSistema Hospitalar.",
                consulation.getPacient().getName(),
                consulationStatus,
                consulation.getNameProfessional(),
                consulation.getDate(),
                consulation.getLocalTime(),
                consulation.getReason()
        );

        message.setText(emailBody);

        return message;
    }

    public void sendEmail(Consulation dto) {
        try {
            SimpleMailMessage message = chooseMessageEmail(dto);
            mailSender.send(message);
            logger.info("Email enviado com sucesso!");
        } catch (MailException e) {
            logger.info("Erro ao enviar email {}", e.getMessage());
            throw new EmailNotSentException("Não foi possível enviar o email, tente novamente mais tarde.");
        }
    }
}

