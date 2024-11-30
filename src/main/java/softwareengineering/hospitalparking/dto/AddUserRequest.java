package softwareengineering.hospitalparking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String loginId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String rrn;
    private String carNumber;
}
