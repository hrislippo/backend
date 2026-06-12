package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.ActivityReq;
import lippo.hris.system.recruitment.request.TemplateReq;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentTemplateValidation {

    public void addRecruitmentTemplateRequired(TemplateReq templateReq) {
        if(templateReq.getCode() == null || templateReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(templateReq.getName() == null || templateReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
