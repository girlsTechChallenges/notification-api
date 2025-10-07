package api.service;

import api.domain.model.Consult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailScheduler {

    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    private final ConsultService consultService;
    private final EmailService emailService;

    public EmailScheduler(ConsultService consultService, EmailService emailService) {
        this.consultService = consultService;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 1000)
    public void processEmails() {
        List<Consult> consults = new ArrayList<>();
        consultService.getEmailQueue().drainTo(consults);

        if (consults.isEmpty()) {
            return;
        }

        logger.info("Processando {} emails da fila", consults.size());

        for (Consult consult : consults) {
            try {
                emailService.sendEmail(consult);
            } catch (Exception e) {
                logger.error("Erro ao enviar email para {}: {}", consult.getPacient().getEmail(), e.getMessage(), e);
                 consultService.getEmailQueue().add(consult);
            }
        }
    }
}
