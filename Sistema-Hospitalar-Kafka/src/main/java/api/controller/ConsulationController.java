package api.controller;

import api.domain.model.Consulation;
import api.service.ConsulationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulations")
public class ConsulationController {
    private final ConsulationService consulationService;

    private final Logger logger = LoggerFactory.getLogger(ConsulationController.class);

    public ConsulationController(ConsulationService consulationService) {
        this.consulationService = consulationService;
    }

    @PostMapping
    public String sendConsultation(@RequestBody Consulation consulation) throws JsonProcessingException {
        logger.info("CONSULATION REQUEST {} ", consulation);
        consulationService.processConsultation(consulation);
        logger.info("CONSULATION HAS BEEN SENT");
        // VER O QUE RETORNAR AQUI
        return "Consulta enviada para o Kafka!";
    }
}
