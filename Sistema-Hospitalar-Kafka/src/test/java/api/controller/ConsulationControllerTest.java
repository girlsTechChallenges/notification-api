package api.controller;

import api.domain.model.Consulation;
import api.service.ConsulationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsulationControllerTest {

    private ConsulationService consulationService;
    private ConsulationController consulationController;

    @BeforeEach
    void setUp() {
        consulationService = mock(ConsulationService.class);
        consulationController = new ConsulationController(consulationService);
    }

    @Test
    void testSendConsultation_ShouldCallServiceAndReturnMessage() throws JsonProcessingException {
        Consulation consulation = new Consulation();
        consulation.setId(1L);
        consulation.setNomePaciente("João");
        consulation.setNomeProfissional("Dr. Silva");

        String response = consulationController.sendConsultation(consulation);

        verify(consulationService, times(1)).processConsultation(consulation);

        assertEquals("Consulta enviada para o Kafka!", response);
    }
}

