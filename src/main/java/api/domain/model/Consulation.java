package api.domain.model;

import api.enums.ConsulationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consulation {
    private String id;
    private String nameProfessional;
    private Patient pacient;
    private String localTime;
    private String date;
    private String reason;
    private String statusConsulation;
}
