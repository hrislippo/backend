package lippo.hris.system.emailengine.controller;

import lippo.hris.system.emailengine.request.EmailTemplateReq;
import lippo.hris.system.emailengine.service.EmailTemplateService;
import lippo.hris.system.emailengine.validation.EmailTemplateValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailTemplateController {

    @Autowired
    EmailTemplateService emailTemplateService;

    @Autowired
    EmailTemplateValidation emailTemplateValidation;

    @PostMapping("/template")
    public ApiResponse addEmailTemplate(@RequestBody EmailTemplateReq emailTemplateReq) {
        emailTemplateValidation.registerEmailTemplateRequired(emailTemplateReq);
        emailTemplateService.addEmailTemplate(emailTemplateReq);
        return ApiResponse.ok(null, "Email Template Saved");
    }

    @PutMapping("/template")
    public ApiResponse modifyEmailTemplate(@RequestParam Long id,
            @RequestBody EmailTemplateReq emailTemplateReq) {
        emailTemplateValidation.registerEmailTemplateRequired(emailTemplateReq);
        emailTemplateService.modifyEmailTemplate(id, emailTemplateReq);
        return ApiResponse.ok(null, "Email Template Modified");
    }

    @GetMapping("/template")
    public ApiResponse getEmailTemplate(@RequestParam(value = "code", required = false) String code,
                                        @RequestParam(value = "name", required = false) String name,
                                        Pageable pageable) {
        return ApiResponse.ok(emailTemplateService.getEmailTemplate(code, name, pageable), "Get Email Template Successfully");
    }

    @GetMapping("/template-list")
    public ApiResponse getEmailTemplateList() {
        return ApiResponse.ok(emailTemplateService.getEmailTemplateList(), "Get Email Template List Successfully");
    }

    @GetMapping("/template-detail")
    public ApiResponse getEmailTemplateDetail(@RequestParam Long id) {
        return ApiResponse.ok(emailTemplateService.getEmailTemplate(id), "Get Email Template Successfully");
    }

    @DeleteMapping("/template")
    public ApiResponse deleteEmailTemplate(@RequestParam Long id) {
        emailTemplateService.deleteEmailTemplate(id);
        return ApiResponse.ok(null, "Email Template Deleted");
    }
}
