package softwareengineering.hospitalparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softwareengineering.hospitalparking.domain.User;
import softwareengineering.hospitalparking.service.ParkingService;

import java.io.IOException;


@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationViewController {
    private final ParkingService parkingService;

    @GetMapping
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/confirmation")
    public String reserveParkingSpace(
            Model model,
            @RequestParam String start,
            @RequestParam String duration,
            @RequestParam String parkingSpace,
            @AuthenticationPrincipal User user) throws IOException {
        model.addAttribute("start", start);
        model.addAttribute("duration", duration);
        model.addAttribute("parkingSpace", parkingSpace);
        return "reservationConfirmation"; // reservationConfirmation.html을 반환
    }
}
