package lippo.hris.system.authentication.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String email;
    private String username;
    private String password;
    private String newPassword;
}
