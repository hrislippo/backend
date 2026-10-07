package lippo.hris.system.authentication.controller;

import lippo.hris.system.authentication.request.TempAuthRequest;
import lippo.hris.system.authentication.service.TemplateAuthService;
import lippo.hris.system.authentication.validation.TempAuthValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class TemplateAuthController {

    @Autowired
    TemplateAuthService templateAuthService;

    @Autowired
    TempAuthValidation tempAuthValidation;

    @PostMapping("/tempauth")
    public ApiResponse addTemplateAuth(@RequestBody TempAuthRequest tempAuthRequest) {
        tempAuthValidation.codeNameRequired(tempAuthRequest);
        tempAuthValidation.organizationRequired(tempAuthRequest);
        tempAuthValidation.organizationDuplicate(tempAuthRequest);
        templateAuthService.addTemplateAuth(tempAuthRequest);
        return ApiResponse.ok(null, "Add Authorization Template Successfully");
    }

    @PutMapping("/tempauth")
    public ApiResponse modifyTemplateAuth(@RequestBody TempAuthRequest tempAuthRequest) {
        tempAuthValidation.codeNameRequired(tempAuthRequest);
        tempAuthValidation.organizationRequired(tempAuthRequest);
        tempAuthValidation.organizationDuplicate(tempAuthRequest);
        templateAuthService.modifyTemplateAuth(tempAuthRequest);
        return ApiResponse.ok(null, "Add Authorization Template Successfully");
    }

    @GetMapping("/tempauth")
    public ApiResponse getTemplateAuth(@RequestParam(value = "templateCode", required = false) String templateCode,
                                     @RequestParam(value = "templateName", required = false) String templateName,
                                     Pageable pageable) {
        return ApiResponse.ok(templateAuthService.getTemplateAuth(templateCode, templateName, pageable), "Get Template Auth Successfully");
    }

    @GetMapping("/tempauth-list")
    public ApiResponse getTemplateAuthList(){
        return ApiResponse.ok(templateAuthService.getTemplateAuthList(), "Get Template Auth List Successfully");
    }

    @GetMapping("/tempauth-detail")
    public ApiResponse getTemplateAuthDetail(@RequestParam("templateId") Long templateId) {
        return ApiResponse.ok(templateAuthService.getTemplateAuthDetail(templateId), "Get Template Auth Detail Successfully");
    }

    @GetMapping("/org-active")
    public ApiResponse getActiveOrganization() {
        return ApiResponse.ok(templateAuthService.getActiveOrganization(), "Get Active Organization Successfully");
    }

    @DeleteMapping("/tempauth")
    public ApiResponse deleteTemplateAuth(@RequestParam("templateId") Long templateId) {
        tempAuthValidation.templateUsed(templateId);
        templateAuthService.deleteTemplateAuth(templateId);
        return ApiResponse.ok(null, "Delete Template Auth Successfully");
    }
}
