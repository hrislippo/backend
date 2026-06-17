package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.request.RoleRequest;
import lippo.hris.system.authentication.validation.UserRoleValidation;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.authentication.service.UserRoleService;
import lippo.hris.system.authentication.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserValidation userValidation;

    @Autowired
    UserRoleValidation userRoleValidation;

    @PutMapping("/user-role")
    public ApiResponse updateRole(@RequestBody RoleRequest roleRequest) {
        User user = userValidation.userValidation(roleRequest.getUsername(), false);
        userRoleValidation.noRole(roleRequest);
        userRoleService.updateRole(roleRequest, user);
        return ApiResponse.ok(null, "Role Updated");
    }

    @GetMapping("/user-role")
    public ApiResponse getUserRoles(@RequestParam String username) {
        User user = userValidation.userValidation(username, false);
        return ApiResponse.ok(userRoleService.getUserRoles(user), "Get user roles successfully");
    }
}
