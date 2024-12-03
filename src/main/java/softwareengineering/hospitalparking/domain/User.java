package softwareengineering.hospitalparking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) // 해당 엔티티 클래스에 auditing 기능을 포함한다
public class User implements UserDetails { // UserDetails를 상속받아 인증 객체로 사용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "login_id", updatable = false)
    private String loginId;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "name", nullable = false)
    private String name; // 이름
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "rrn", nullable = false)
    private String rrn; // 주민등록 번호
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // 휴대폰 번호
    @Column(name = "car_number", nullable = false)
    private String carNumber; // 차량 번호
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles; // 사용자 권한을 저장하는 필드

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public User(String loginId, String name, String phoneNumber, String rrn, String carNumber, String email, String password, Set<String> roles) {
        this.loginId = loginId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.rrn = rrn; // 주민등록번호 설정
        this.carNumber = carNumber;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.createdAt = LocalDateTime.now(); // 생성 시각 설정
    }

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true = 잠금되지 않았음
    }

    // 패스워드 잠금 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true = 잠금되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true = 잠금되지 않았음
    }

    /**
     * getAuthorities, getUsername, isAccountNonExpired, isCredentialsNonExpired, isEnabled
     * Override 해줘야함
     * */
}
