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
    public ApiResponse getStructure(@RequestParam(value = "empNIK") String empNIK,
                                    @RequestParam(value = "posName") String posName,
                                    @RequestParam(value = "subordinateDepth") Integer subordinateDepth,
                                    @RequestParam(value = "superiorDepth") Integer superiorDepth,
                                    Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(personnelStructureService.getEmployeeStructure(empNIK, posName, subordinateDepth, superiorDepth, customUserDetails.getAuthorizations()), "Get Personnel Structure Successfully");
    }
}
