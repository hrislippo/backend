package lippo.hris.system.talentmanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.talentmanagement.request.TalentPoolDetailReq;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
import lippo.hris.system.talentmanagement.service.TalentPoolService;
import lippo.hris.system.talentmanagement.validation.TalentPoolValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talentmanagement")
public class TalentPoolController {

    @Autowired
    TalentPoolValidation talentPoolValidation;

    @Autowired
    TalentPoolService talentPoolService;

    @PostMapping("/talentpool")
    public ApiResponse saveTalentPool(@RequestBody List<TalentPoolReq> talentPoolReq) {
        talentPoolValidation.nikRequired(talentPoolReq);
        talentPoolValidation.nikDuplicate(talentPoolReq);
        talentPoolService.saveTalentPool(talentPoolReq);
        return ApiResponse.ok(null, "Save Talent Pool Successfully");
    }

    @PutMapping("/talentpool")
    public ApiResponse modifyTalentPool(@RequestBody TalentPoolReq talentPoolReq) {
        talentPoolValidation.reasonRequired(talentPoolReq);
        talentPoolService.modifyTalentPool(talentPoolReq);
        return ApiResponse.ok(null, "Update Talent Pool Successfully");
    }


    @GetMapping("/employees")
    public ApiResponse getActiveEmployees() {
        return ApiResponse.ok(talentPoolService.getAllActiveEmployee(), "Get Active Employee Successfully");
    }

    @GetMapping("/talentpool")
    public ApiResponse getTalentPool(@RequestParam(value = "employeeNIK", required = false) String employeeNIK,
                                     @RequestParam(value = "employeeName", required = false) String employeeName,
                                     Pageable pageable) {
        return ApiResponse.ok(talentPoolService.getAllTalentPool(employeeNIK, employeeName, pageable), "Get Talent Pool Successfully");
    }

    @GetMapping("/talentpool-list")
    public ApiResponse getTalentPoolList() {
        return ApiResponse.ok(talentPoolService.getAllTalentPool(), "Get Talent Pool List Successfully");
    }

    @GetMapping("/talentpool-detail")
    public ApiResponse getTalentPoolDetail(@RequestParam(value = "employeeNIK") String employeeNIK) {
        return ApiResponse.ok(talentPoolService.getTalentPoolDetail(employeeNIK), "Get Talent Pool Detail Successfully");
    }

    @GetMapping("/positions")
    public ApiResponse getActivePositions() {
        return ApiResponse.ok(talentPoolService.getAllActivePosition(), "Get Active Positions Successfully");
    }
}
