package softwareengineering.hospitalparking.dto;

import lombok.Getter;
import lombok.Setter;
import softwareengineering.hospitalparking.domain.User;

@Getter
@Setter
public class ParkingReservationResponseDTO {
    private String start;
    private int duration;
    private String parkingSpace;
}
