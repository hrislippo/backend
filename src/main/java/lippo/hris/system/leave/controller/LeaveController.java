package lippo.hris.system.leave.controller;

import lippo.hris.system.leave.service.LeaveService;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    @PostMapping("/import-leave")
    public ApiResponse importLeave(@RequestParam("file") MultipartFile file,
                                 Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        leaveService.importLeave(file, userDetails.getUsername());
        return ApiResponse.ok(null, "Leave imported from Excel");
    }

    @GetMapping("/download-import-leave")
    public ResponseEntity<?> downloadImportLeave() throws IOException {
        return leaveService.downloadImportLeave();
    }
}
