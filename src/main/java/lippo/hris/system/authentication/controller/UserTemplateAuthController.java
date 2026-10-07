package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.request.UserTempAuthReq;
import lippo.hris.system.authentication.service.UserTempAuthService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class UserTemplateAuthController {

    @Autowired
    UserTempAuthService userTempAuthService;

    @PutMapping("/user-tempauth")
    public ApiResponse modifyUserTemplateAuth(@RequestBody UserTempAuthReq userTempAuthReq) {
        userTempAuthService.modifyUserTempAuth(userTempAuthReq);
        return ApiResponse.ok(null, "Modify User Template Auth Successfully");
    }

    @GetMapping("/user-tempauth")
    public ApiResponse getUserTemplateAuth(@RequestParam("username") String username) {
        return ApiResponse.ok(userTempAuthService.getTemplateAuthList(username), "Get User Template Auth Successfully");
    }
}
