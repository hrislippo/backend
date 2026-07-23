package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.request.LoginRequest;
import lippo.hris.system.authentication.request.UserActiveRequest;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.authentication.service.LoginService;
import lippo.hris.system.authentication.service.UserService;
import lippo.hris.system.authentication.validation.UserValidation;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class UserController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    UserValidation userValidation;

    @PostMapping("/user")
    public ApiResponse register(@RequestBody LoginRequest loginRequest) {
        userValidation.registerUserRequired(loginRequest);
        userValidation.userValidation(loginRequest.getUsername(), true);
        userService.registerUser(loginRequest);
        return ApiResponse.ok(null, "User registered");
    }

    @PutMapping("/user")
    public ApiResponse changePassword(@RequestBody LoginRequest loginRequest) {
        userValidation.changePasswordRequired(loginRequest);
        User user = userValidation.userValidation(loginRequest.getUsername(), false);
        userValidation.passwordValidation(loginRequest);
        userService.changePassword(loginRequest, user);
        return ApiResponse.ok(null, "Password changed");
    }

    @PutMapping("/user-active")
    public ApiResponse changeActive(@RequestBody UserActiveRequest userActiveRequest) {
        User user = userValidation.userValidation(userActiveRequest.getUsername(), false);
        userService.changeActive(userActiveRequest.getActive(), user);
        return ApiResponse.ok(null, "Active changed");
    }

    @GetMapping("/user")
    public ApiResponse getUsers(@RequestParam(value = "usernameSearch", required = false) String usernameSearch,
                                @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                @RequestParam(value = "roleSearch", required = false) String roleSearch,
                                @RequestParam(value = "activeSearch", required = false) Boolean activeSearch,
                                Pageable pageable) {
        return ApiResponse.ok(userService.findAllWithRolesAndSearch(usernameSearch, nameSearch, roleSearch, activeSearch, pageable),
                "Get Users Successfully");
    }

    @GetMapping("/user-profile")
    public ApiResponse getUserProfile(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(userService.getUserProfile(customUserDetails.getUsername()),
                "Get User Profile");
    }

    @GetMapping("/user-list")
    public ApiResponse getUserLists(@RequestParam(value = "role", required = false) String role) {
        return ApiResponse.ok(userService.findAllUsers(role), "Get List Users Successfully");
    }

    @GetMapping("/user-detail")
    public ApiResponse getUserDetail(@RequestParam String username) {
        return ApiResponse.ok(userService.findByUsername(username), "Get User Detail Successfully");
    }

    @DeleteMapping("/user")
    public ApiResponse deleteUser(@RequestParam String username) {
        User user = userValidation.userValidation(username, false);
        userService.deleteUser(user);
        return ApiResponse.ok(null, "Delete User Successfully");
    }

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
