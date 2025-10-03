package api.entrypoint.dto.response;

public record ConsulationResponseDto(
        String message,
        String consulationId,
        String consulationStatus
) {}

