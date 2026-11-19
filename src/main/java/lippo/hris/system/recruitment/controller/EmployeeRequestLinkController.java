package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.EmployeeRequestLinkReq;
import lippo.hris.system.recruitment.service.EmployeeRequestLinkService;
import lippo.hris.system.recruitment.validation.EmployeeRequestLinkValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class EmployeeRequestLinkController {

    @Autowired
    EmployeeRequestLinkValidation employeeRequestLinkValidation;

    @Autowired
    EmployeeRequestLinkService employeeRequestLinkService;

    @PostMapping("/generate-erf-link")
    public ApiResponse generateLink(@RequestBody EmployeeRequestLinkReq employeeRequestLinkReq){
        employeeRequestLinkValidation.inputRequired(employeeRequestLinkReq);
        return ApiResponse.ok(employeeRequestLinkService.generateLink(employeeRequestLinkReq), "Employee Request Link Generated");
    }

    @GetMapping("/erf-link")
    public ApiResponse getLink(@RequestParam("id") Long id){
        return ApiResponse.ok(employeeRequestLinkService.getLink(id), "Get Employee Request Link Successfully");
    }
}
