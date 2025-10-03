package api.entrypoint.controller;

import api.domain.model.Consult;
import api.mapper.ConsultMapper;
import api.service.ConsultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsultControllerTest {

    private ConsultService consultService;
    private ConsultMapper consultMapper;
    private ConsultController consultController;

    @BeforeEach
    void setUp() {
        consultService = mock(ConsultService.class);
        consultMapper = mock(ConsultMapper.class);
        consultController = new ConsultController(consultService, consultMapper);
    }

}

