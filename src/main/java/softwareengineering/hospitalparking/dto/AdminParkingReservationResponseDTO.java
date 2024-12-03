package softwareengineering.hospitalparking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminParkingReservationResponseDTO {
    private Long id;
    private String name;
    private String carNumber;
    private String start;
    private int duration;
    private String parkSpace;
}
