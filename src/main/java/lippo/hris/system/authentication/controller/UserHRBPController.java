package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.request.UserHRBPRequest;
import lippo.hris.system.authentication.service.UserHRBPService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class UserHRBPController {

    @Autowired
    UserHRBPService userHRBPService;

    @PutMapping("/user-hrbp")
    public ApiResponse updateHRBP(@RequestBody UserHRBPRequest userHRBPRequest) {
        userHRBPService.updateHRBP(userHRBPRequest);
        return ApiResponse.ok(null, "HRBP Updated");
    }

    @GetMapping("/user-hrbp")
    public ApiResponse getUserHRBP(@RequestParam String username) {
        return ApiResponse.ok(userHRBPService.getUserHRBP(username), "Get user HRBP successfully");
    }
}
