package softwareengineering.hospitalparking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationGetRequestDTO {
    private String start;
    private int duration;
}
