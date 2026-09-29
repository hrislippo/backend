package lippo.hris.system.talentmanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
import lippo.hris.system.talentmanagement.request.TalentPoolRequestReq;
import lippo.hris.system.talentmanagement.service.TalentPoolRequestService;
import lippo.hris.system.talentmanagement.validation.TalentPoolRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talentmanagement")
public class TalentPoolRequestController {

    @Autowired
    TalentPoolRequestValidation talentPoolRequestValidation;

    @Autowired
    TalentPoolRequestService talentPoolRequestService;

    @PostMapping("/talentpoolrequest")
    public ApiResponse saveTalentPoolRequest(@RequestBody TalentPoolRequestReq talentPoolRequestReq) {
        talentPoolRequestValidation.talentRequired(talentPoolRequestReq);
        talentPoolRequestValidation.talentDuplicate(talentPoolRequestReq);
        talentPoolRequestValidation.positionRequired(talentPoolRequestReq);
        talentPoolRequestValidation.positionExists(talentPoolRequestReq);
        talentPoolRequestService.saveTalentPoolRequest(talentPoolRequestReq);
        return ApiResponse.ok(null, "Save Talent Pool Request Successfully");
    }

    @PutMapping("/talentpoolrequest")
    public ApiResponse modifyTalentPoolRequest(@RequestBody TalentPoolRequestReq talentPoolRequestReq) {
        talentPoolRequestValidation.talentRequired(talentPoolRequestReq);
        talentPoolRequestValidation.talentDuplicate(talentPoolRequestReq);
        talentPoolRequestValidation.positionRequired(talentPoolRequestReq);
        talentPoolRequestService.modifyTalentPoolRequest(talentPoolRequestReq);
        return ApiResponse.ok(null, "Modify Talent Pool Request Successfully");
    }

    @GetMapping("/talentpoolrequest")
    public ApiResponse getTalentPoolRequest(@RequestParam(value = "positionName", required = false) String positionName,
                                            @RequestParam(value = "pilarName", required = false) String pilarName,
                                            @RequestParam(value = "buName", required = false) String buName,
                                            Pageable pageable){
        return ApiResponse.ok(talentPoolRequestService.findTalentPoolRequests(positionName, pilarName, buName, pageable), "Get Talent Pool Request Successfully");
    }

    @GetMapping("/talentpoolrequest-detail")
    public ApiResponse getTalentPoolRequestDetail(@RequestParam(value = "positionCode") String positionCode){
        return ApiResponse.ok(talentPoolRequestService.findTalentPoolRequestDetail(positionCode), "Get Talent Pool Request Detail Successfully");
    }

    @GetMapping("/talentpoolrequest-ninebox")
    public ApiResponse getTalentPoolRequestNineBox(@RequestParam(value = "positionCode", required = false) String positionCode){
        return ApiResponse.ok(talentPoolRequestService.findTalentPoolRequestNineBox(positionCode), "Get Talent Pool Request Nine Box Successfully");
    }
}
