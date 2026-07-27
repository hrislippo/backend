package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.request.LoginRequest;
import lippo.hris.system.authentication.service.LoginService;
import lippo.hris.system.authentication.validation.UserValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserValidation userValidation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userValidation.userValidation(loginRequest.getUsername(), false);
        userValidation.userActiveValidation(user);
        return loginService.loginUser(loginRequest, user);
    }

    @PostMapping("/refresh")
    public ApiResponse refresh(@CookieValue("refreshToken") String refreshToken){
        return ApiResponse.ok(loginService.refresh(refreshToken), "Token Refreshed");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return loginService.logoutUser();
    }
}
