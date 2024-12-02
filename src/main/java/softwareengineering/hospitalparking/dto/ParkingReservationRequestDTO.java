package softwareengineering.hospitalparking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParkingReservationRequestDTO {
    private String start;
    private int duration;
    private String parkingSpace;
}
