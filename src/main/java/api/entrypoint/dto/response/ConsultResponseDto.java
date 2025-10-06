package api.entrypoint.dto.response;

public record ConsultResponseDto(
        String message,
        String consultId,
        String consultStatus
) {}

