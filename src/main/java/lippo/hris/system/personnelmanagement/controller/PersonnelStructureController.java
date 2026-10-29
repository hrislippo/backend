package lippo.hris.system.personnelmanagement.controller;

import lippo.hris.system.personnelmanagement.service.PersonnelStructureService;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personnelmanagement")
public class PersonnelStructureController {

    @Autowired
    PersonnelStructureService personnelStructureService;

    @GetMapping("/employee")
    public ApiResponse getEmployee(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "position", required = false) String position,
                                   Pageable pageable) {
        return ApiResponse.ok(personnelStructureService.getEmployee(name, position, pageable), "Get Personnel Structure Employee Successfully");
    }

    @GetMapping("/structure")
    public ApiResponse getStructure(@RequestParam(value = "empNIK", required = false) String empNIK,
                                    @RequestParam(value = "posCode") String posCode,
                                    @RequestParam(value = "subordinateDepth") Integer subordinateDepth,
                                    @RequestParam(value = "superiorDepth") Integer superiorDepth) {
        return ApiResponse.ok(personnelStructureService.getEmployeeStructure(empNIK, posCode, subordinateDepth, superiorDepth), "Get Personnel Structure Successfully");
    }
}
