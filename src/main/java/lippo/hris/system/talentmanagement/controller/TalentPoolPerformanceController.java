package lippo.hris.system.talentmanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.talentmanagement.service.TalentPoolPerformanceService;
import lippo.hris.system.talentmanagement.service.TalentPoolPotentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/talentmanagement")
public class TalentPoolPerformanceController {

    @Autowired
    TalentPoolPerformanceService talentPoolPerformanceService;

    @GetMapping("/talentpoolperformance")
    public ApiResponse getTalentPoolPerformance() {
        return ApiResponse.ok(talentPoolPerformanceService.findAllPerformance(), "Get Talent Pool Performance Successfully");
    }
}
