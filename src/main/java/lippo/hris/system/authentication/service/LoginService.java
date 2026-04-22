package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserRole;
import lippo.hris.system.exception.UnauthorizedException;
import lippo.hris.system.authentication.repository.UserRoleRepository;
import lippo.hris.system.authentication.request.LoginRequest;
import lippo.hris.system.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LoginService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    JwtUtil jwtUtil;

    public String loginUser(LoginRequest loginRequest, User user){
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }

        List<String> userRoles = userRoleRepository.findByUser(user.getUsername());
        return jwtUtil.generateToken(user.getUsername(), userRoles);
    }
}

