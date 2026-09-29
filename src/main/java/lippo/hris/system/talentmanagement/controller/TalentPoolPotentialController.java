package lippo.hris.system.talentmanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.talentmanagement.service.TalentPoolPotentialService;
import lippo.hris.system.talentmanagement.service.TalentPoolReadinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/talentmanagement")
public class TalentPoolPotentialController {

    @Autowired
    TalentPoolPotentialService talentPoolPotentialService;

    @GetMapping("/talentpoolpotential")
    public ApiResponse getTalentPoolPotential() {
        return ApiResponse.ok(talentPoolPotentialService.findAllPotential(), "Get Talent Pool Potential Successfully");
    }
}
