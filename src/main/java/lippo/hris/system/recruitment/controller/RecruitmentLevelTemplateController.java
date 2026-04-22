package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.LevelTemplateReq;
import lippo.hris.system.recruitment.service.RecruitmentLevelTemplateService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class RecruitmentLevelTemplateController {

    @Autowired
    RecruitmentLevelTemplateService recruitmentLevelTemplateService;

    @PostMapping("/level-template")
    public ApiResponse addLevelTemplate(@RequestBody LevelTemplateReq levelTemplateReq) {
        recruitmentLevelTemplateService.addLevelTemplate(levelTemplateReq);
        return ApiResponse.ok(null, "Level Template Saved");
    }

    @PutMapping("/level-template")
    public ApiResponse modifyLevelTemplate(@RequestBody LevelTemplateReq levelTemplateReq) {
        recruitmentLevelTemplateService.modifyLevelTemplate(levelTemplateReq);
        return ApiResponse.ok(null, "Level Template Modified");
    }

    @GetMapping("/level-template")
    public ApiResponse getLevelTemplate(@RequestParam(value = "code", required = false) String code,
                                        @RequestParam(value = "name", required = false) String name,
                                        Pageable pageable) {
        return ApiResponse.ok(recruitmentLevelTemplateService.getLevelTemplate(code, name, pageable), "Get Level Template Successfully");
    }

    @GetMapping("/level-template-list")
    public ApiResponse getLevelTemplateList() {
        return ApiResponse.ok(recruitmentLevelTemplateService.getLevelTemplateList(), "Get Level Template List Successfully");
    }

    @GetMapping("/level-template-detail")
    public ApiResponse getLevelTemplateDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(recruitmentLevelTemplateService.getLevelTemplateDetail(id), "Get Level Template Detail Successfully");
    }

    @DeleteMapping("/level-template")
    public ApiResponse deleteLevelTemplate(@RequestParam(value = "id") Long id) {
        recruitmentLevelTemplateService.deleteLevelTemplate(id);
        return ApiResponse.ok(null, "Level Template Deleted");
    }
}
