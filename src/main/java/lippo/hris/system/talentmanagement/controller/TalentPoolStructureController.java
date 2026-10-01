package lippo.hris.system.talentmanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.talentmanagement.service.TalentPoolStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/talentmanagement")
public class TalentPoolStructureController {

    @Autowired
    TalentPoolStructureService talentPoolStructureService;

    @GetMapping("/talent")
    public ApiResponse getTalent(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "position", required = false) String position,
                                   Pageable pageable) {
        return ApiResponse.ok(talentPoolStructureService.getTalent(name, position, pageable), "Get Talent Successfully");
    }

    @GetMapping("/structure")
    public ApiResponse getStructure(@RequestParam(value = "empNIK") String empNIK,
                                    @RequestParam(value = "posName") String posName,
                                    @RequestParam(value = "subordinateDepth") Integer subordinateDepth,
                                    @RequestParam(value = "superiorDepth") Integer superiorDepth){
        return ApiResponse.ok(talentPoolStructureService.getTalentStructure(empNIK, posName, subordinateDepth, superiorDepth), "Get Talent Structure Successfully");
    }
}
