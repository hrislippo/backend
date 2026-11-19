package lippo.hris.system.recruitment.controllerpublic;

import lippo.hris.system.recruitment.request.EmployeeRequestPersonalDataReq;
import lippo.hris.system.recruitment.service.EmployeeRequestSyncService;
import lippo.hris.system.recruitment.validation.EmployeeRequestLinkValidation;
import lippo.hris.system.recruitment.validation.EmployeeRequestSyncValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/recruitment")
public class EmployeeRequestSyncControllerPublic {

    @Autowired
    EmployeeRequestSyncService employeeRequestSyncService;

    @Autowired
    EmployeeRequestLinkValidation employeeRequestLinkValidation;

    @Autowired
    EmployeeRequestSyncValidation employeeRequestSyncValidation;

    @PutMapping("/erf-personal-data")
    public ApiResponse modifyPersonalData(@RequestBody EmployeeRequestPersonalDataReq employeeRequestPersonalDataReq){
        employeeRequestSyncValidation.inputRequired(employeeRequestPersonalDataReq);
        employeeRequestLinkValidation.existingLink(employeeRequestPersonalDataReq.getToken());
        employeeRequestSyncService.inputPersonalData(employeeRequestPersonalDataReq);
        return ApiResponse.ok(null, "Employee Request Personal Data Saved");
    }

    @GetMapping("/gender-list")
    public ApiResponse getGenderList() {
        return ApiResponse.ok(employeeRequestSyncService.getGenderList(), "Get Gender List Successfully");
    }

    @GetMapping("/marital-status-list")
    public ApiResponse getMaritalStatusList() {
        return ApiResponse.ok(employeeRequestSyncService.getMaritalStatusList(), "Get Marital Status List Successfully");
    }

    @GetMapping("/religion-list")
    public ApiResponse getReligionList() {
        return ApiResponse.ok(employeeRequestSyncService.getReligionList(), "Get Religion List Successfully");
    }

    @GetMapping("/erf-personal-data")
    public ApiResponse getPersonalData(@RequestParam("token") String token){
        employeeRequestLinkValidation.existingLink(token);
        return ApiResponse.ok(employeeRequestSyncService.getPersonalData(token), "Get Employee Request Personal Data Successfully");
    }
}
