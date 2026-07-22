package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.authentication.repository.UserRoleRepository;
import lippo.hris.system.authentication.request.LoginRequest;
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
    JwtUtil jwtUtil;

    public ResponseEntity<?> loginUser(LoginRequest loginRequest, User user){
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Authentication failed. Please try again");
        }

        List<String> userRoles = userRoleRepository.findByUser(user.getUsername());
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), userRoles);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), userRoles);

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
                        "accessToken",
                        accessToken
                ));
    }

    public String refresh(String refreshToken){
        if(!jwtUtil.validateToken(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtUtil.getUsername(refreshToken);
        List<String> userRoles = userRoleRepository.findByUser(username);
        return jwtUtil.generateAccessToken(username, userRoles);
    }

    public ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = ResponseCookie.from("refreshToken","")
                        .httpOnly(true)
                        .maxAge(0)
                        .path("/")
                        .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}

