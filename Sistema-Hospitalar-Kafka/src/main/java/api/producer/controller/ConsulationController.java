package api.producer.controller;

import api.dto.ConsulationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulations")
public class ConsulationController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String TOPICO = "consulations";

    @PostMapping
    public String sendConsultation(@RequestBody ConsulationDTO consulation) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String mensagem = objectMapper.writeValueAsString(consulation);
        kafkaTemplate.send(TOPICO, mensagem);
        return "Consulta enviada para o Kafka!";
    }
}
