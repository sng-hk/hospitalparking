package softwareengineering.hospitalparking.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import softwareengineering.hospitalparking.domain.ParkingReservation;
import softwareengineering.hospitalparking.domain.ParkingSpace;
import softwareengineering.hospitalparking.domain.User;
import softwareengineering.hospitalparking.dto.ParkingReservationRequestDTO;
import softwareengineering.hospitalparking.dto.ParkingReservationResponseDTO;
import softwareengineering.hospitalparking.repository.ParkingReservationRepository;
import softwareengineering.hospitalparking.repository.ParkingSpaceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingReservationRepository parkingReservationRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final EmailService emailService;

    // 주차 예약 생성 메서드
    public ParkingReservation createParkingReservation(ParkingReservationRequestDTO request, User user) {
        LocalDateTime startDateTime = LocalDateTime.parse(request.getStart()); // ISO 8601 형식으로 변환
        LocalDateTime endDateTime = startDateTime.plusHours(request.getDuration()); // 종료 시간 계산

        ParkingReservation reservation = new ParkingReservation();
        reservation.setStart(startDateTime);
        reservation.setEnd(endDateTime);
        ParkingSpace location = parkingSpaceRepository.findByLocation(request.getParkingSpace())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected ParkingSpace"));
        reservation.setParkingSpace(location);
        reservation.setUser(user); // 예약한 사용자 설정
        ParkingReservation savedEmail = parkingReservationRepository.save(reservation);
        // 예약 확인 이메일 전송
        try {
            emailService.sendReservationConfirmation(user.getEmail(), location.getLocation(), startDateTime, endDateTime);
        } catch (MailException e) {
            // 이메일 전송 실패 처리 (로깅 등)
            System.err.println("이메일 전송 실패: " + e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return savedEmail; // 예약 저장
    }

    public List<ParkingSpace> getAvailableParkingSpaces(LocalDateTime start, LocalDateTime end) {
        return parkingSpaceRepository.findAvailableParkingSpaces(start, end);
    }

    public List<ParkingReservationResponseDTO> findReservationList(User user) {
        return parkingReservationRepository.findAllByUserId(user.getId())
                .orElseThrow(() -> new );
    }
}