package api.mapper;

import api.domain.model.Consult;
import api.domain.model.Patient;
import api.entrypoint.dto.request.ConsultRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ConsultMapper {

    public Consult mapperDtoToDomain(ConsultRequestDto dto) {
        Patient patient = new Patient();
        patient.setName(dto.pacient().name());
        patient.setEmail(dto.pacient().email());

        return new Consult(
                dto.id(),
                dto.nameProfessional(),
                patient,
                dto.localTime(),
                dto.date(),
                dto.reason(),
                dto.statusConsult()
        );
    }
}
