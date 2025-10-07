package api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {

    private final JacksonConfig jacksonConfig = new JacksonConfig();

    @Test
    void objectMapper_shouldBeCreated() {
        ObjectMapper mapper = jacksonConfig.objectMapper();
        assertNotNull(mapper, "ObjectMapper não deve ser nulo");
    }

    @Test
    void objectMapper_shouldSerializeJavaTimeObjects() {
        ObjectMapper mapper = jacksonConfig.objectMapper();

        LocalDate date = LocalDate.of(2025, 10, 7);
        assertDoesNotThrow(() -> {
            String json = mapper.writeValueAsString(date);
            assertNotNull(json);
        }, "ObjectMapper deve serializar objetos java.time sem lançar exceção");
    }

    @Test
    void objectMapper_shouldDeserializeJavaTimeObjects() {
        ObjectMapper mapper = jacksonConfig.objectMapper();

        String json = "\"2025-10-07\""; // formato LocalDate padrão
        assertDoesNotThrow(() -> {
            LocalDate date = mapper.readValue(json, LocalDate.class);
            assertEquals(2025, date.getYear());
            assertEquals(10, date.getMonthValue());
            assertEquals(7, date.getDayOfMonth());
        }, "ObjectMapper deve desserializar objetos java.time sem lançar exceção");
    }
}
