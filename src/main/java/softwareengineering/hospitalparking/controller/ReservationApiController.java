package softwareengineering.hospitalparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import softwareengineering.hospitalparking.domain.ParkingReservation;
import softwareengineering.hospitalparking.domain.ParkingSpace;
import softwareengineering.hospitalparking.domain.User;
import softwareengineering.hospitalparking.dto.ParkingReservationRequestDTO;
import softwareengineering.hospitalparking.dto.ParkingReservationResponseDTO;
import softwareengineering.hospitalparking.dto.ReservationGetRequestDTO;
import softwareengineering.hospitalparking.service.ParkingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ParkingService parkingService;

    @PostMapping("/available-spaces")
    public List<ParkingSpace> getAvailableParkingSpaces(@RequestBody ReservationGetRequestDTO request) {
        LocalDateTime startDateTime = LocalDateTime.parse(request.getStart()); // ISO 8601 형식으로 변환
        LocalDateTime endDateTime = startDateTime.plusHours(request.getDuration()); // 시작 시간에 사용 시간을 더하여 종료 시간 계산
        return parkingService.getAvailableParkingSpaces(startDateTime, endDateTime);
    }

    @PostMapping("/reservation")
    public ResponseEntity<String> reserveParkingSpace(
            @RequestParam String start,
            @RequestParam int duration,
            @RequestParam String parkingSpace,
            @AuthenticationPrincipal User user) {

        // DTO 객체 생성 및 데이터 설정
        ParkingReservationRequestDTO request = new ParkingReservationRequestDTO();
        request.setStart(start);
        request.setDuration(duration);
        request.setParkingSpace(parkingSpace);

        // 예약 생성
        parkingService.createParkingReservation(request, user);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/reservationList")
    public ResponseEntity<List<ParkingReservationResponseDTO>> reservationList(@AuthenticationPrincipal User user) {
        List<ParkingReservationResponseDTO> responseDTOs = parkingService.findReservationList(user);
        return ResponseEntity.ok(responseDTOs);
    }
}
