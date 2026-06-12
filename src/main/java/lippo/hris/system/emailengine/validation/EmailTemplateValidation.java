package lippo.hris.system.emailengine.validation;

import lippo.hris.system.emailengine.request.EmailTemplateReq;
import lippo.hris.system.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateValidation {

    public void registerEmailTemplateRequired(EmailTemplateReq emailTemplateReq){
        if(emailTemplateReq.getCode() == null || emailTemplateReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code is required");
        }
        if(emailTemplateReq.getName() == null || emailTemplateReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name is required");
        }
        if(emailTemplateReq.getSubject() == null || emailTemplateReq.getSubject().trim().isEmpty()){
            throw new BadRequestException("Subject is required");
        }
        if(emailTemplateReq.getContentHtml() == null || emailTemplateReq.getContentHtml().trim().isEmpty()){
            throw new BadRequestException("Content HTML is required");
        }
    }
}
