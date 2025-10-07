package api.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consult {
    private String id;
    private String nameProfessional;
    private Patient pacient;
    private String localTime;
    private String date;
    private String reason;
    private String statusConsult;
}
