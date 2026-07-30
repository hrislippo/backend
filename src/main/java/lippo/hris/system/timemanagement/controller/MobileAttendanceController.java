package lippo.hris.system.timemanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrReq;
import lippo.hris.system.timemanagement.service.MobileAttendanceService;
import lippo.hris.system.timemanagement.validation.MobileAttendanceValidation;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/timemanagement")
public class MobileAttendanceController {

    @Autowired
    MobileAttendanceValidation mobileAttendanceValidation;

    @Autowired
    MobileAttendanceService mobileAttendanceService;

    @PostMapping("/mobile-attendance")
    public ApiResponse addMobileAttendance(@RequestBody MOTMAtdTempMbrReq motmAtdTempMbrReq,
                                     Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        mobileAttendanceValidation.checkMobileAttendanceValue(motmAtdTempMbrReq);
        mobileAttendanceValidation.inputMobileAttendanceRequired(motmAtdTempMbrReq);
        mobileAttendanceService.addMobileAttendance(motmAtdTempMbrReq, customUserDetails.getUsername());
        return ApiResponse.ok(null, "Mobile Attendance Added");
    }

    @GetMapping("/mobile-attendance")
    public ApiResponse getMobileAttendance(@RequestParam(value = "empNIK", required = false) String empNIK,
                                     @RequestParam(value = "tempCode", required = false) String tempCode,
                                     @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                     @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                     Pageable pageable) {
        return ApiResponse.ok(mobileAttendanceService.getMobileAttendance(empNIK, tempCode, startDate, endDate, pageable), "Get Mobile Attendance Successfully");
    }

    @GetMapping("/mobile-attendance-detail")
    public ApiResponse getMobileAttendanceDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(mobileAttendanceService.getMobileAttendanceDetail(id), "Get Mobile Attendance Detail Successfully");
    }
}
