package api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ApiErrorMessage> handleEmailNotSentException(EmailNotSentException ex) {
        Map<String, List<String>> errors = Map.of(
                "email", List.of(ex.getMessage())
        );

        ApiErrorMessage errorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorMessage> handleGenericException(Exception ex) {
        Map<String, List<String>> errors = Map.of(
                "general", List.of("Ocorreu um erro inesperado. Tente novamente mais tarde.")
        );

        ApiErrorMessage errorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errors);
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

}
