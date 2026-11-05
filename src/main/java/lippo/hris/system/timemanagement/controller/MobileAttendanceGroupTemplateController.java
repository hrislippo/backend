package lippo.hris.system.timemanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.timemanagement.request.MobileAttendanceGroupTemplateReq;
import lippo.hris.system.timemanagement.service.MobileAttendanceGroupTemplateService;
import lippo.hris.system.timemanagement.validation.MobileAttendanceGroupTemplateValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timemanagement")
public class MobileAttendanceGroupTemplateController {

    @Autowired
    MobileAttendanceGroupTemplateValidation mobileAttendanceGroupTemplateValidation;

    @Autowired
    MobileAttendanceGroupTemplateService mobileAttendanceGroupTemplateService;

    @PostMapping("/mobile-attendance-grouptemp")
    public ApiResponse addMobileAttendanceGroupTemp(@RequestBody MobileAttendanceGroupTemplateReq mobileAttendanceGroupTemplateReq) {
        mobileAttendanceGroupTemplateValidation.codeNameExist(mobileAttendanceGroupTemplateReq);
        mobileAttendanceGroupTemplateValidation.hrbpRequired(mobileAttendanceGroupTemplateReq);
        mobileAttendanceGroupTemplateService.addMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplateReq);
        return ApiResponse.ok(null, "Mobile Attendance Group Template Added");
    }

    @PutMapping("/mobile-attendance-grouptemp")
    public ApiResponse modifyMobileAttendanceGroupTemp(@RequestBody MobileAttendanceGroupTemplateReq mobileAttendanceGroupTemplateReq) {
        mobileAttendanceGroupTemplateValidation.codeNameExist(mobileAttendanceGroupTemplateReq);
        mobileAttendanceGroupTemplateValidation.hrbpRequired(mobileAttendanceGroupTemplateReq);
        mobileAttendanceGroupTemplateService.modifyMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplateReq);
        return ApiResponse.ok(null, "Mobile Attendance Group Template Modified");
    }

    @GetMapping("/mobile-attendance-grouptemp")
    public ApiResponse getMobileAttendanceGroupTemp(@RequestParam(value = "codeSearch", required = false) String codeSearch,
                                                    @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                                    @RequestParam(value = "buSearch", required = false) String buSearch,
                                                    @RequestParam(value = "hrbpSearch", required = false) String hrbpSearch,
                                                    Pageable pageable) {
        return ApiResponse.ok(mobileAttendanceGroupTemplateService.getMobileAttendanceGroupTemplate(codeSearch, nameSearch, buSearch, hrbpSearch, pageable), "Get Mobile Attendance Group Template Successfully");
    }

    @GetMapping("/mobile-attendance-grouptemp-detail")
    public ApiResponse getMobileAttendanceGroupTempDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(mobileAttendanceGroupTemplateService.getMobileAttendanceGroupTemplateDetail(id), "Get Mobile Attendance Group Template Detail Successfully");
    }
}
