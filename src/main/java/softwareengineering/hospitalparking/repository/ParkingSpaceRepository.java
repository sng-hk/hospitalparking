package softwareengineering.hospitalparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import softwareengineering.hospitalparking.domain.ParkingSpace;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    /**
     * SELECT ps FROM ParkingSpace ps:
     * ParkingSpace 엔티티에서 모든 주차 공간(ps)을 선택합니다.
     *
     * WHERE ps.id NOT IN:
     * NOT IN 조건을 사용하여 주차 공간의 ID가 특정 목록에 포함되지 않는 주차 공간만 선택합니다.
     * 이 목록은 예약된 주차 공간의 ID입니다.
     *
     * (SELECT pr.parkingSpace.id FROM ParkingReservation pr WHERE (pr.start < :end) AND (pr.end > :start)):
     *
     * 내부 쿼리: ParkingReservation 엔티티에서 시작 시간(pr.start)이 요청된 종료 시간(:end)보다 작고, 종료 시간(pr.end)이 요청된 시작 시간(:start)보다 큰 예약을 찾습니다.
     * 이 조건을 만족하는 예약의 주차 공간 ID를 선택합니다.
     * 즉, 주어진 기간에 예약된 주차 공간의 ID 목록을 가져옵니다.
     *
     * @Param("start") 및 @Param("end"):
     * start와 end는 메서드에 전달된 매개변수로, 예약을 확인할 시간 범위를 정의합니다.
     * */
    @Query("SELECT ps FROM ParkingSpace ps WHERE ps.id NOT IN " +
            "(SELECT pr.parkingSpace.id FROM ParkingReservation pr " +
            "WHERE (pr.start < :end) AND (pr.end > :start))")
    List<ParkingSpace> findAvailableParkingSpaces(@Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end);

    Optional<ParkingSpace> findByLocation(String location);
}
