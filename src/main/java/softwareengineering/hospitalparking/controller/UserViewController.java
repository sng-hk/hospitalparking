package softwareengineering.hospitalparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "html/login";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "html/signup";
    }
}
