package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.Role;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserRole;
import lippo.hris.system.authentication.mapper.UserMapper;
import lippo.hris.system.authentication.repository.RoleRepository;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.authentication.repository.UserRoleRepository;
import lippo.hris.system.authentication.request.LoginRequest;
import lippo.hris.system.authentication.response.UserProfileResponse;
import lippo.hris.system.authentication.response.UserResponse;
import lippo.hris.system.authentication.response.UserResponsev2;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.recruitment.repository.EmployeeRequestPICRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmployeeRequestPICRepository employeeRequestPICRepository;

    public void registerUser(LoginRequest loginRequest){
        loginRequest.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        User newUser = userMapper.toEntity(loginRequest);
        newUser = userRepository.saveAndFlush(newUser);

        Role role = roleRepository.findByRole("ROLE_USER").get();
        UserRole userRole = new UserRole();
        userRole.setUser(newUser);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
    }

    public void changePassword(LoginRequest loginRequest, User user){
        user.setPassword(passwordEncoder.encode(loginRequest.getNewPassword()));
        userRepository.save(user);
    }

    public void changeActive(Boolean active, User user){
        user.setActive(active);
        userRepository.save(user);
    }

    public List<UserResponsev2> findAllUsers(String role){
        List<UserResponsev2> list = new ArrayList<>();
        list.add(null);
        if(role != null){
            list.addAll(userRepository.findByActiveTrueAndRole(role));
        } else{
            list.addAll(userRepository.findByActiveTrue());
        }
        return list;
    }

    public UserResponsev2 findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserProfileResponse getUserProfile(String username){
        User user = userRepository.findByusername(username).orElse(null);

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setUsername(username);
        userProfileResponse.setName(user.getName());
        return userProfileResponse;
    }

    public void deleteUser(User user){
        if(!employeeRequestPICRepository.findByUser(user).isEmpty()){
            throw new ConflictException("User cannot be deleted due to become ERF PIC already");
        }

        userRoleRepository.deleteByUser(user);
        userRepository.delete(user);
    }

    public Page<UserResponse> findAllWithRolesAndSearch(String usernameSearch,
                                                        String nameSearch,
                                                        String roleSearch,
                                                        Boolean activeSearch,
                                                        Pageable pageable){
        return userRepository.findAllWithRolesAndSearch(usernameSearch, nameSearch, roleSearch, activeSearch, pageable);
    }
}
