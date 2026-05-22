package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.service.MenuService;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public ApiResponse getMenu(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(menuService.getMenu(customUserDetails.getRoles()), "Get Menu Successfully");
    }
}
