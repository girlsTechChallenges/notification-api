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
        message.setTo(consulation.getEmailPaciente());

        if (consulation.getStatus() == ConsulationStatus.REALIZADA) {
            message.setSubject("Realização de Consulta");
        } else {
            message.setSubject("Confirmação de Consulta");
        }

        ConsulationEmailStatus consulationStatus;

        if (consulation.getStatus() == ConsulationStatus.AGENDADA) {
            consulationStatus = ConsulationEmailStatus.CONFIRMADA;
        } else if (consulation.getStatus() == ConsulationStatus.EDITADA) {
            consulationStatus = ConsulationEmailStatus.REAGENDADA;
        } else {
            consulationStatus = ConsulationEmailStatus.REALIZADA;
        }

        String emailBody = String.format(
                "Olá %s,%nSua consulta foi %s com %s.%n%nData/Hora: %s%nMotivo: %s%n%nAtenciosamente,%nSistema Hospitalar.",
                consulation.getNomePaciente(),
                consulationStatus,
                consulation.getNomeProfissional(),
                consulation.getDataHora(),
                consulation.getMotivo()
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

