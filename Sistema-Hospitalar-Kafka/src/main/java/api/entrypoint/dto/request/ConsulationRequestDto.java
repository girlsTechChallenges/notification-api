package api.entrypoint.dto.request;

import api.common.annotation.ValidDate;
import api.common.annotation.ValidLocalTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ConsulationRequestDto(
        @NotBlank(message = "Id name cannot be null or empty")
        String id,

        @NotBlank(message = "Professional name cannot be null or empty")
        String nameProfessional,

        @NotNull(message = "Pacient cannot be null")
        @Valid
        PacientDto pacient,

        @NotNull(message = "Local time cannot be null")
        @ValidLocalTime
        @Pattern(
                regexp = "([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d",
                message = "Time must follow the format HH:mm:ss"
        )
        String localTime,

        @NotNull(message = "Date cannot be null")
        @ValidDate
        @Pattern(
                regexp = "\\d{4}-\\d{2}-\\d{2}",
                message = "Date must follow the format yyyy-MM-dd"
        )
        String date,

        @NotBlank(message = "Reason cannot be null or empty")
        String reason,

        @NotBlank(message = "Status cannot be null or empty")
        @Pattern(
                regexp = "SCHEDULED|CANCELLED|CARRIED_OUT",
                message = "Status must be SCHEDULED, CANCELLED, or DONE"
        )
        String statusConsulation
) {
    public record PacientDto(
            @NotBlank(message = "Pacient name cannot be null or empty")
            String name,

            @NotBlank(message = "Pacient email cannot be null or empty")
            @Email(message = "Pacient email must be valid")
            String email
    ) {}
}

