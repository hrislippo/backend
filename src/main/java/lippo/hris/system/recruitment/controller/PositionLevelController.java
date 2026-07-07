package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.EmploymentTypeReq;
import lippo.hris.system.recruitment.request.PositionLevelReq;
import lippo.hris.system.recruitment.service.PositionLevelService;
import lippo.hris.system.recruitment.validation.PositionLevelValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class PositionLevelController {

    @Autowired
    PositionLevelService positionLevelService;

    @Autowired
    PositionLevelValidation positionLevelValidation;

    @PostMapping("/position-level")
    public ApiResponse addPositionLevel(@RequestBody PositionLevelReq positionLevelReq) {
        positionLevelValidation.addPositionLevelRequired(positionLevelReq);
        positionLevelService.addPositionLevel(positionLevelReq);
        return ApiResponse.ok(null, "Position Level Saved");
    }

    @PutMapping("/position-level")
    public ApiResponse modifyPositionLevel(@RequestBody PositionLevelReq positionLevelReq) {
        positionLevelValidation.addPositionLevelRequired(positionLevelReq);
        positionLevelService.modifyPositionLevel(positionLevelReq);
        return ApiResponse.ok(null, "Position Level Modified");
    }

    @GetMapping("/position-level")
    public ApiResponse getPositionLevel(@RequestParam(value = "code", required = false) String code,
                                         @RequestParam(value = "name", required = false) String name,
                                         Pageable pageable) {
        return ApiResponse.ok(positionLevelService.getPositionLevel(code, name, pageable), "Get Position Level Successfully");
    }

    @GetMapping("/position-level-list")
    public ApiResponse getPositionLevelList() {
        return ApiResponse.ok(positionLevelService.getPositionLevel(), "Get Position Level List Successfully");
    }

    @GetMapping("/position-level-detail")
    public ApiResponse getPositionLevelDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(positionLevelService.getPositionLevel(id), "Get Position Level Successfully");
    }

    @DeleteMapping("/position-level")
    public ApiResponse deletePositionLevel(@RequestParam Long id) {
        positionLevelService.deletePositionLevel(id);
        return ApiResponse.ok(null, "Position Level Deleted");
    }
}
