package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.TemplateReq;
import lippo.hris.system.recruitment.service.RecruitmentTemplateService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class RecruitmentTemplateController {

    @Autowired
    RecruitmentTemplateService recruitmentTemplateService;

    @PostMapping("/template")
    public ApiResponse addTemplate(@RequestBody TemplateReq templateReq) {
        recruitmentTemplateService.addTemplate(templateReq);
        return ApiResponse.ok(null, "Template Saved");
    }

    @PutMapping("/template")
    public ApiResponse modifyTemplate(@RequestBody TemplateReq templateReq) {
        recruitmentTemplateService.modifyTemplate(templateReq);
        return ApiResponse.ok(null, "Template Modified");
    }

    @GetMapping("/template")
    public ApiResponse getTemplate(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "name", required = false) String name,
                                   Pageable pageable){
        return ApiResponse.ok(recruitmentTemplateService.getTemplate(code, name, pageable), "Get Template Successfully");
    }

    @GetMapping("/template-list")
    public ApiResponse getTemplateList(){
        return ApiResponse.ok(recruitmentTemplateService.getTemplateList(), "Get Template Successfully");
    }

    @GetMapping("/template-detail")
    public ApiResponse getTemplateDetail(@RequestParam(value = "id") Long id){
        return ApiResponse.ok(recruitmentTemplateService.getTemplateDetail(id), "Get Template Detail Successfully");
    }

    @DeleteMapping("/template")
    public ApiResponse deleteTemplate(@RequestParam(value = "id") Long id){
        recruitmentTemplateService.deleteTemplate(id);
        return ApiResponse.ok(null, "Template Deleted");
    }
}
