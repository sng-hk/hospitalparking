package softwareengineering.hospitalparking.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softwareengineering.hospitalparking.domain.ParkingReservation;
import softwareengineering.hospitalparking.dto.AdminParkingReservationResponseDTO;
import softwareengineering.hospitalparking.service.ParkingService;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ParkingService parkingService;
    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/reservationList")
    public String reservationList(Model model) {
        List<ParkingReservation> reservationList = parkingService.findReservationList();
        List<AdminParkingReservationResponseDTO> list = new ArrayList<>();
        for(int i = 0; i < reservationList.size(); i++) {
            AdminParkingReservationResponseDTO response = new AdminParkingReservationResponseDTO();
            ParkingReservation reservation = reservationList.get(i);
            response.setId(reservation.getId());
            response.setName(reservation.getUser().getName());
            response.setStart(reservation.getStart().toString());
            response.setCarNumber(reservation.getUser().getCarNumber());
            response.setDuration((int) Duration.between(reservation.getStart(), reservation.getEnd()).toHours());
            list.add(response);
        }
        model.addAttribute("reservationList", list);
        return "admin_reservationList";
    }
}
