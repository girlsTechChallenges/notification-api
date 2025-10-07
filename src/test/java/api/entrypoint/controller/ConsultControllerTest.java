package api.entrypoint.controller;

import api.domain.model.Consult;
import api.entrypoint.dto.request.ConsultRequestDto;
import api.entrypoint.dto.request.ConsultRequestDto.PacientDto;
import api.mapper.ConsultMapper;
import api.service.ConsultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultController.class)
class ConsultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConsultService consultService;

    @MockBean
    private ConsultMapper consultMapper;

    private ConsultRequestDto validRequestDto;
    private Consult validConsult;

    @BeforeEach
    void setUp() {
        PacientDto pacientDto = new PacientDto("Jorginho", "jorginho@gmail.com");

        validRequestDto = new ConsultRequestDto(
                "1",
                "Dra. Maria Silva",
                pacientDto,
                "14:30:00",
                "2025-10-03",
                "Consulta de rotina",
                "SCHEDULED"
        );

        validConsult = new Consult();
        validConsult.setId("1");
        validConsult.setStatusConsult("SCHEDULED");

        Mockito.when(consultMapper.mapperDtoToDomain(any(ConsultRequestDto.class)))
                .thenReturn(validConsult);
    }

    @Test
    void shouldReturn200AndValidResponse() throws Exception {
        mockMvc.perform(post("/consults")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consulta adicionada à fila para envio de e-mail!"))
                .andExpect(jsonPath("$.consultId").value("1"))
                .andExpect(jsonPath("$.consultStatus").value("SCHEDULED"));

        // Verifica se o service foi chamado uma vez
        verify(consultService, times(1)).processConsult(validConsult);
        verify(consultMapper, times(1)).mapperDtoToDomain(any(ConsultRequestDto.class));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        // DTO inválido: id vazio
        ConsultRequestDto invalidRequest = new ConsultRequestDto(
                "",
                "Dra. Maria Silva",
                validRequestDto.pacient(),
                "14:30:00",
                "2025-10-03",
                "Consulta de rotina",
                "SCHEDULED"
        );

        mockMvc.perform(post("/consults")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verifica que o mapper/service não foi chamado
        verify(consultMapper, times(0)).mapperDtoToDomain(any());
        verify(consultService, times(0)).processConsult(any());
    }
}
