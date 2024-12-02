package softwareengineering.hospitalparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softwareengineering.hospitalparking.domain.User;
import softwareengineering.hospitalparking.dto.ParkingReservationResponseDTO;
import softwareengineering.hospitalparking.service.ParkingService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final ParkingService parkingService;
    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model,
                           @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        List<ParkingReservationResponseDTO> reservationList = parkingService.findReservationList(user);
        model.addAttribute("reservationList", reservationList);
        return "mypageThymeleaf";
    }
}
