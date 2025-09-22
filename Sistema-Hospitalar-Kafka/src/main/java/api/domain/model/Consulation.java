package api.domain.model;

import api.enums.ConsulationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consulation {
    private Long id;
    private String nomePaciente;
    private String emailPaciente;
    private String nomeProfissional;
    private LocalDateTime dataHora;
    private String motivo;
    private ConsulationStatus status;
}
