package softwareengineering.hospitalparking.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softwareengineering.hospitalparking.domain.User;

@Controller
public class UserViewController {

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
        return "mypageThymeleaf";
    }
}
