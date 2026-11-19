package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.EmployeeRequestEmployeeDataReq;
import lippo.hris.system.recruitment.request.EmployeeRequestPersonalDataReq;
import lippo.hris.system.recruitment.request.EmployeeRequestSyncReq;
import lippo.hris.system.recruitment.service.EmployeeRequestSyncService;
import lippo.hris.system.recruitment.validation.EmployeeRequestSyncValidation;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class EmployeeRequestSyncController {

    @Autowired
    EmployeeRequestSyncService employeeRequestSyncService;

    @Autowired
    EmployeeRequestSyncValidation employeeRequestSyncValidation;

    @PostMapping("/sync-erf")
    public ApiResponse syncEmployeeRequest(@RequestBody EmployeeRequestSyncReq employeeRequestSyncReq,
                                           Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        employeeRequestSyncService.syncEmployeeRequest(employeeRequestSyncReq, customUserDetails.getUsername());
        return ApiResponse.ok(null, "Employee Request Sync Successfully");
    }

    @PutMapping("/erf-employee-data")
    public ApiResponse modifyEmployeeData(@RequestBody EmployeeRequestEmployeeDataReq employeeRequestEmployeeDataReq){
        employeeRequestSyncValidation.inputRequired(employeeRequestEmployeeDataReq);
        employeeRequestSyncService.inputEmployeeData(employeeRequestEmployeeDataReq);
        return ApiResponse.ok(null, "Employee Request Employee Data Saved");
    }

    @PutMapping("/erf-personal-data")
    public ApiResponse modifyPersonalData(@RequestBody EmployeeRequestPersonalDataReq employeeRequestPersonalDataReq){
        employeeRequestSyncValidation.inputRequired(employeeRequestPersonalDataReq);
        employeeRequestSyncService.inputPersonalData(employeeRequestPersonalDataReq);
        return ApiResponse.ok(null, "Employee Request Personal Data Saved");
    }

    @GetMapping("/erf-employee-data")
    public ApiResponse getEmployeeData(@RequestParam("id") Long id){
        return ApiResponse.ok(employeeRequestSyncService.getEmployeeData(id), "Get Employee Request Employee Data Successfully");
    }

    @GetMapping("/erf-personal-data")
    public ApiResponse getPersonalData(@RequestParam("id") Long id){
        return ApiResponse.ok(employeeRequestSyncService.getPersonalData(id), "Get Employee Request Personal Data Successfully");
    }

    @GetMapping("/related-info-position")
    public ApiResponse getRelatedInfoPosition(@RequestParam(value = "posCode", required = false) String posCode) {
        return ApiResponse.ok(employeeRequestSyncService.getRelatedInfoPosition(posCode), "Get Related Info Position Successfully");
    }

    @GetMapping("/marital-status-list")
    public ApiResponse getMaritalStatusList() {
        return ApiResponse.ok(employeeRequestSyncService.getMaritalStatusList(), "Get Marital Status List Successfully");
    }

    @GetMapping("/religion-list")
    public ApiResponse getReligionList() {
        return ApiResponse.ok(employeeRequestSyncService.getReligionList(), "Get Religion List Successfully");
    }

    @GetMapping("/gender-list")
    public ApiResponse getGenderList() {
        return ApiResponse.ok(employeeRequestSyncService.getGenderList(), "Get Gender List Successfully");
    }
}
