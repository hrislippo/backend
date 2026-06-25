package lippo.hris.system.authentication.validation;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.request.LoginRequest;
import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.exception.NotFoundException;
import lippo.hris.system.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserValidation {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public User userValidation(String username, Boolean isExists){
        User user = userRepository.findByusername(username).orElse(null);
        if(!isExists){
            if(user == null){
                throw new NotFoundException("User is not found");
            }
        }else{
            if(user != null){
                throw new ConflictException("Username is already exists");
            }
        }
        return user;
    }

    public void passwordValidation(LoginRequest loginRequest){
        User user = userRepository.findByusername(loginRequest.getUsername()).orElse(null);
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadRequestException("Existing Password not match");
        }
    }

    public void userActiveValidation(User user){
        if(user == null || !user.getActive()){
            throw new ConflictException("User is not active");
        }
    }

    public void registerUserRequired(LoginRequest loginRequest){
        if(loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()){
            throw new BadRequestException("Username is required");
        }
        if(loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()){
            throw new BadRequestException("Email is required");
        }
        if(loginRequest.getName() == null || loginRequest.getName().trim().isEmpty()){
            throw new BadRequestException("Name is required");
        }
        if(loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()){
            throw new BadRequestException("Password is required");
        }
    }

    public void changePasswordRequired(LoginRequest loginRequest){
        if(loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()){
            throw new BadRequestException("Password is required");
        }
        if(loginRequest.getNewPassword() == null || loginRequest.getNewPassword().trim().isEmpty()){
            throw new BadRequestException("New Password is required");
        }
    }
}
