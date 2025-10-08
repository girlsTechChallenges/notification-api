package api.mapper;

import api.domain.model.Consult;
import api.entrypoint.dto.request.ConsultRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsultMapperTest {

    private ConsultMapper consultMapper;

    @BeforeEach
    void setUp() {
        consultMapper = new ConsultMapper();
    }

    @Test
    void shouldMapDtoToDomainCorrectly() {
        ConsultRequestDto.PacientDto pacientDto = new ConsultRequestDto.PacientDto(
                "Jorginho",
                "jorginho@gmail.com"
        );

        ConsultRequestDto dto = new ConsultRequestDto(
                "1",
                "Dra. Maria Silva",
                pacientDto,
                "14:30:00",
                "2025-10-03",
                "Consulta de rotina",
                "SCHEDULED"
        );

        Consult consult = consultMapper.mapperDtoToDomain(dto);

        assertNotNull(consult);
        assertEquals("1", consult.getId());
        assertEquals("Dra. Maria Silva", consult.getNameProfessional());
        assertNotNull(consult.getPatient());
        assertEquals("Jorginho", consult.getPatient().getName());
        assertEquals("jorginho@gmail.com", consult.getPatient().getEmail());
        assertEquals("14:30:00", consult.getLocalTime());
        assertEquals("2025-10-03", consult.getDate());
        assertEquals("Consulta de rotina", consult.getReason());
        assertEquals("SCHEDULED", consult.getStatusConsult());
    }

    @Test
    void shouldReturnConsultWithEmptyFieldsIfDtoHasEmptyValues() {
        ConsultRequestDto.PacientDto pacientDto = new ConsultRequestDto.PacientDto("", "");
        ConsultRequestDto dto = new ConsultRequestDto("", "", pacientDto, "", "", "", "");

        Consult consult = consultMapper.mapperDtoToDomain(dto);

        assertNotNull(consult);
        assertEquals("", consult.getId());
        assertEquals("", consult.getNameProfessional());
        assertNotNull(consult.getPatient());
        assertEquals("", consult.getPatient().getName());
        assertEquals("", consult.getPatient().getEmail());
        assertEquals("", consult.getLocalTime());
        assertEquals("", consult.getDate());
        assertEquals("", consult.getReason());
        assertEquals("", consult.getStatusConsult());
    }
}
