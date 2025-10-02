package api.mapper;

import api.domain.model.Consulation;
import api.domain.model.Patient;
import api.entrypoint.dto.request.ConsulationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ConsulationMapper {

    public Consulation mapperDtoToDomain(ConsulationRequestDto dto) {
        Patient patient = new Patient();
        patient.setName(dto.pacient().name());
        patient.setEmail(dto.pacient().email());

        return new Consulation(
                dto.id(),
                dto.nameProfessional(),
                patient,
                dto.localTime(),
                dto.date(),
                dto.reason(),
                dto.statusConsulation()
        );
    }
}
