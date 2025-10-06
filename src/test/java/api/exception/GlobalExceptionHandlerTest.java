package api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleEmailNotSentException() {
        EmailNotSentException ex = new EmailNotSentException("Erro ao enviar email");

        ResponseEntity<ApiErrorMessage> response = handler.handleEmailNotSentException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getErrors().containsKey("email"));
        assertEquals("Erro ao enviar email", response.getBody().getErrors().get("email").get(0));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Erro ao inciar a aplicação");

        ResponseEntity<ApiErrorMessage> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getErrors().containsKey("general"));
        assertEquals("Ocorreu um erro inesperado. Tente novamente mais tarde.",
                response.getBody().getErrors().get("general").get(0));
    }

    @Test
    void testHandleValidationErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "Mensagem de erro");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("field"));
        assertEquals("Mensagem de erro", response.getBody().get("field"));
    }
}

