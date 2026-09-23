package lippo.hris.system.talentmanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.talentmanagement.request.TalentPoolDetailReq;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
import lippo.hris.system.talentmanagement.service.TalentPoolService;
import lippo.hris.system.talentmanagement.validation.TalentPoolValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/talentmanagement")
public class TalentPoolController {

    @Autowired
    TalentPoolValidation talentPoolValidation;

    @Autowired
    TalentPoolService talentPoolService;

    @PostMapping("/talentpool")
    public ApiResponse saveTalentPool(@RequestBody TalentPoolReq talentPoolReq) {
        talentPoolValidation.positionRequired(talentPoolReq);
        talentPoolValidation.nikRequired(talentPoolReq);
        talentPoolValidation.nikDuplicate(talentPoolReq);
        talentPoolValidation.positionExists(talentPoolReq);
        talentPoolService.saveTalentPool(talentPoolReq);
        return ApiResponse.ok(null, "Save Talent Pool Successfully");
    }

    @PutMapping("/talentpool")
    public ApiResponse modifyTalentPool(@RequestBody TalentPoolDetailReq talentPoolDetailReq) {
        talentPoolValidation.nikRequired(talentPoolDetailReq);
        talentPoolValidation.nikDuplicate(talentPoolDetailReq);
        talentPoolService.modifyTalentPool(talentPoolDetailReq);
        return ApiResponse.ok(null, "Update Talent Pool Successfully");
    }

    @GetMapping("/talentpool")
    public ApiResponse getTalentPool(@RequestParam(value = "positionCode", required = false) String positionCode,
                                     @RequestParam(value = "positionName", required = false) String positionName,
                                     Pageable pageable) {
        return ApiResponse.ok(talentPoolService.getAllTalentPool(positionCode, positionName, pageable), "Get Talent Pool Successfully");
    }

    @GetMapping("/talentpool-detail")
    public ApiResponse getTalentPoolDetail(@RequestParam(value = "positionCode") String positionCode) {
        return ApiResponse.ok(talentPoolService.getTalentPoolDetail(positionCode), "Get Talent Pool Detail Successfully");
    }

    @GetMapping("/positions")
    public ApiResponse getActivePositions() {
        return ApiResponse.ok(talentPoolService.getAllActivePosition(), "Get Active Positions Successfully");
    }
}
