package api.entrypoint.controller;

import api.domain.model.Consulation;
import api.entrypoint.dto.request.ConsulationRequestDto;
import api.entrypoint.dto.response.ConsulationResponseDto;
import api.mapper.ConsulationMapper;
import api.service.ConsulationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/consulations")
public class ConsulationController {
    private final ConsulationService consulationService;
    private final ConsulationMapper consulationMapper;

    private final Logger logger = LoggerFactory.getLogger(ConsulationController.class);

    public ConsulationController(ConsulationService consulationService, ConsulationMapper consulationMapper) {
        this.consulationService = consulationService;
        this.consulationMapper = consulationMapper;
    }

    @PostMapping
    public ResponseEntity<ConsulationResponseDto> sendConsultation(@Valid @RequestBody ConsulationRequestDto consulationDto) throws JsonProcessingException {
        logger.info("CONSULATION REQUEST {} ", consulationDto);

        Consulation consulation = consulationMapper.mapperDtoToDomain(consulationDto);

        consulationService.processConsultation(consulation);

        logger.info("CONSULATION HAS BEEN SENT");

        ConsulationResponseDto response = new ConsulationResponseDto(
                "Consulta enviada com sucesso para o Kafka!",
                consulation.getId(),
                consulation.getStatusConsulation()
        );

        return ResponseEntity.ok(response);
    }
}
