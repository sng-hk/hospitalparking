package softwareengineering.hospitalparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softwareengineering.hospitalparking.domain.User;
import softwareengineering.hospitalparking.dto.AddUserRequest;
import softwareengineering.hospitalparking.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        // 빌더 패턴 생성자 사용
        return userRepository.save(User.builder()
                .email(dto.getEmail()) // set Email
                // 패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // set Password
                .build()).getId();
    }
}
