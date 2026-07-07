package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.EmploymentTypeReq;
import lippo.hris.system.recruitment.service.EmploymentTypeService;
import lippo.hris.system.recruitment.validation.EmploymentTypeValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class EmploymentTypeController {

    @Autowired
    EmploymentTypeService employmentTypeService;

    @Autowired
    EmploymentTypeValidation employmentTypeValidation;

    @PostMapping("/employment-type")
    public ApiResponse addEmploymentType(@RequestBody EmploymentTypeReq employmentTypeReq) {
        employmentTypeValidation.addEmploymentTypeRequired(employmentTypeReq);
        employmentTypeService.addEmploymentType(employmentTypeReq);
        return ApiResponse.ok(null, "Employment Type Saved");
    }

    @PutMapping("/employment-type")
    public ApiResponse modifyEmploymentType(@RequestBody EmploymentTypeReq employmentTypeReq) {
        employmentTypeValidation.addEmploymentTypeRequired(employmentTypeReq);
        employmentTypeService.modifyEmploymentType(employmentTypeReq);
        return ApiResponse.ok(null, "Employment Type Modified");
    }

    @GetMapping("/employment-type")
    public ApiResponse getEmploymentType(@RequestParam(value = "code", required = false) String code,
                                @RequestParam(value = "name", required = false) String name,
                                Pageable pageable) {
        return ApiResponse.ok(employmentTypeService.getEmploymentType(code, name, pageable), "Get Employment Type Successfully");
    }

    @GetMapping("/employment-type-list")
    public ApiResponse getEmploymentTypeList() {
        return ApiResponse.ok(employmentTypeService.getEmploymentType(), "Get Employment Type List Successfully");
    }

    @GetMapping("/employment-type-detail")
    public ApiResponse getEmploymentTypeDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employmentTypeService.getEmploymentType(id), "Get Employment Type Successfully");
    }

    @DeleteMapping("/employment-type")
    public ApiResponse deleteEmploymentType(@RequestParam Long id) {
        employmentTypeService.deleteEmploymentType(id);
        return ApiResponse.ok(null, "Employment Type Deleted");
    }
}
