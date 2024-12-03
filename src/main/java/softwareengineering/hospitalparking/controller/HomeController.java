package softwareengineering.hospitalparking.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import softwareengineering.hospitalparking.domain.User;

import java.util.Set;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // 현재 인증 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자 여부 확인
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            User principal = (User) authentication.getPrincipal();
            if (principal.getRoles().contains("ROLE_ADMIN")) {
                return "admin";
            }
            // 인증된 사용자일 경우
            return "homeLoggedIn"; // 로그인한 상태의 뷰 이름
        }
        // 인증되지 않은 사용자일 경우
        return "homeLoggedOut"; // 로그인하지 않은 상태의 뷰 이름
    }
}
