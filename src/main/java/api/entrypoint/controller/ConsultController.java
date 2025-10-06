package api.entrypoint.controller;

import api.domain.model.Consult;
import api.entrypoint.dto.request.ConsultRequestDto;
import api.entrypoint.dto.response.ConsultResponseDto;
import api.mapper.ConsultMapper;
import api.service.ConsultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consults")
public class ConsultController {
    private final ConsultService consultService;
    private final ConsultMapper consultMapper;

    private final Logger logger = LoggerFactory.getLogger(ConsultController.class);

    public ConsultController(ConsultService consultService, ConsultMapper consultMapper) {
        this.consultService = consultService;
        this.consultMapper = consultMapper;
    }

    @PostMapping
    public ResponseEntity<ConsultResponseDto> sendConsultation(@Valid @RequestBody ConsultRequestDto consultDto) throws JsonProcessingException {
        logger.info("CONSULT REQUEST {} ", consultDto);

        Consult consult = consultMapper.mapperDtoToDomain(consultDto);

        consultService.processConsult(consult);

        logger.info("CONSULT HAS BEEN SENT");

        ConsultResponseDto response = new ConsultResponseDto(
                "Consulta enviada com sucesso para o Kafka!",
                consult.getId(),
                consult.getStatusConsult()
        );

        return ResponseEntity.ok(response);
    }
}
