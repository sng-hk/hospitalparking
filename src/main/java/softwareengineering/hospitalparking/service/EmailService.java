package softwareengineering.hospitalparking.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine; // Thymeleaf 템플릿 엔진 추가

    public void sendReservationConfirmation(String to, String parkingSpace, LocalDateTime start, LocalDateTime end) throws MessagingException {
        // 이메일 템플릿에 전달할 변수 설정
        Context context = new Context();
        context.setVariable("parkingSpace", parkingSpace);
        context.setVariable("start", start);
        context.setVariable("end", end);

        // 템플릿 엔진을 사용하여 HTML 콘텐츠 생성
        String htmlContent = templateEngine.process("reservationConfirmationMail", context);

        // MimeMessage를 사용하여 HTML 이메일 생성
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true를 통해 multipart로 설정
        helper.setTo(to);
        helper.setSubject("주차 예약 확인");
        helper.setText(htmlContent, true); // 두 번째 인자를 true로 설정하여 HTML로 전송
        mailSender.send(message);
    }
}
