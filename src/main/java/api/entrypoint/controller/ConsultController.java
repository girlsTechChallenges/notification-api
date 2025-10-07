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
import org.springframework.web.bind.annotation.*;

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

        logger.info("Consulta adicionada à fila para envio de email (id: {})", consult.getId());

        return ResponseEntity.ok(new ConsultResponseDto(
                "Consulta adicionada à fila para envio de e-mail!",
                consult.getId(),
                consult.getStatusConsult()
        ));
    }
}
