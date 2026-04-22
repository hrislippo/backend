package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.BusinessUnitReq;
import lippo.hris.system.recruitment.service.BusinessUnitService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class BusinessUnitController {

    @Autowired
    BusinessUnitService businessUnitService;

    @PostMapping("/business-unit")
    public ApiResponse addBusinessUnit(@RequestBody BusinessUnitReq businessUnitReq) {
        businessUnitService.addBusinessUnit(businessUnitReq);
        return ApiResponse.ok(null, "Business Unit Saved");
    }

    @PutMapping("/business-unit")
    public ApiResponse modifyBusinessUnit(@RequestBody BusinessUnitReq businessUnitReq) {
        businessUnitService.modifyBusinessUnit(businessUnitReq);
        return ApiResponse.ok(null, "Business Unit Modified");
    }

    @GetMapping("/business-unit")
    public ApiResponse getBusinessUnit(@RequestParam(value = "code", required = false) String code,
                                       @RequestParam(value = "name", required = false) String name,
                                       Pageable pageable) {
        return ApiResponse.ok(businessUnitService.getBusinessUnit(code, name, pageable), "Get Business Unit Successfully");
    }

    @GetMapping("/business-unit-list")
    public ApiResponse getBusinessUnitList() {
        return ApiResponse.ok(businessUnitService.getBusinessUnitList(), "Get Business Unit List Successfully");
    }

    @GetMapping("/business-unit-detail")
    public ApiResponse getBusinessUnitDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(businessUnitService.getBusinessUnitDetail(id), "Get Business Unit Successfully");
    }

    @DeleteMapping("/business-unit")
    public ApiResponse deleteBusinessUnit(@RequestParam("id") Long id) {
        businessUnitService.deleteBusinessUnit(id);
        return ApiResponse.ok(null, "Business Unit Deleted");
    }
}
