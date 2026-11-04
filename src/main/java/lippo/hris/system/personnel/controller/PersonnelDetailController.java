package lippo.hris.system.personnel.controller;

import lippo.hris.system.personnel.service.PersonnelService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personnelmanagement")
public class PersonnelDetailController {

    @Autowired
    PersonnelService personnelService;

    @GetMapping("/employee-detail")
    public ApiResponse getEmployeeDetail(@RequestParam(value = "empNIK") String empNIK) {
        return ApiResponse.ok(personnelService.getEmployeeDetail(empNIK), "Get Employee Detail Successfully");
    }
}
