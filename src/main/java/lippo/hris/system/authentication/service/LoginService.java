package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.PermissionRoleRepository;
import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.authentication.repository.UserRoleRepository;
import lippo.hris.system.authentication.request.LoginRequest;
import lippo.hris.system.service.AuditLogService;
import lippo.hris.system.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoginService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PermissionRoleRepository permissionRoleRepository;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Autowired
    AuditLogService auditLogService;

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<?> loginUser(LoginRequest loginRequest, User user){

        loginAttemptService.checkLocked(user);
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            loginAttemptService.loginFailed(user);
            auditLogService.log("("+user.getUsername()+") Login Failed");
            throw new BadRequestException("Authentication failed. Please try again");
        }

        try{
            List<String> userRoles = userRoleRepository.findByUser(user.getUsername());
            List<String> userPermissions = permissionRoleRepository.findByUser(user.getUsername());

            String accessToken = jwtUtil.generateAccessToken(user.getUsername(), userRoles, userPermissions);
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), userRoles, userPermissions);
            loginAttemptService.loginSucceeded(user);
            auditLogService.log("("+user.getUsername()+") Login Successfully");

            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofDays(7))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(Map.of(
                            "accessToken", accessToken,
                            "permissions", userPermissions
                    ));
        } catch(Exception e){
            loginAttemptService.loginFailed(user);
            auditLogService.log("("+user.getUsername()+") Login Failed");
            throw new BadRequestException("Authentication failed. Please try again");
        }
    }

    public String refresh(String refreshToken){
        if(!jwtUtil.validateToken(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtUtil.getUsername(refreshToken);
        List<String> userRoles = userRoleRepository.findByUser(username);
        List<String> userPermissions = permissionRoleRepository.findByUser(username);
        return jwtUtil.generateAccessToken(username, userRoles, userPermissions);
    }

    public ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = ResponseCookie.from("refreshToken","")
                        .httpOnly(true)
                        .maxAge(0)
                        .path("/")
                        .build();

        auditLogService.log("Logout Successfully");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}

