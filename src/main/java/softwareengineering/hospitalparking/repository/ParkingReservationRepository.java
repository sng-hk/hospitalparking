package softwareengineering.hospitalparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softwareengineering.hospitalparking.domain.ParkingReservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingReservationRepository extends JpaRepository<ParkingReservation, Long> {
    public Optional<List<ParkingReservation>> findAllByUserId(Long userId);
}
