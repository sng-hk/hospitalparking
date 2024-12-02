package softwareengineering.hospitalparking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "parking_space")
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @OneToMany(mappedBy = "parkingSpace", fetch = FetchType.LAZY)
    @JsonIgnore // 예약 목록을 직렬화하지 않음
    private List<ParkingReservation> reservations; // 예약 목록
}
