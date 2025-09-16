package api.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsulationDTO {
    private Long id;
    private String nomePaciente;
    private String nomeProfissional;
    private LocalDateTime dataHora;
    private String motivo;
    private String status;
}
