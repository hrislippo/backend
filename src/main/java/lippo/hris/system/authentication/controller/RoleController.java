package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.service.RoleService;
import lippo.hris.system.authentication.validation.RoleValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleValidation roleValidation;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role")
    public ApiResponse saveRole(@RequestParam(value = "roleName") String roleName) {
        roleValidation.duplicateRole(roleName);
        roleService.saveRole(roleName);
        return ApiResponse.ok(null, "Save role successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role")
    public ApiResponse getRoles() {;
        return ApiResponse.ok(roleService.getRoles(), "Get roles successfully");
    }
}
