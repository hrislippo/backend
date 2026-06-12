package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.ActivityReq;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentActivityValidation {

    public void addRecruitmentActivityRequired(ActivityReq activityReq) {
        if(activityReq.getGroup() == null || activityReq.getGroup().trim().isEmpty()){
            throw new BadRequestException("Group activity cannot be empty");
        }

        if(activityReq.getCode() == null || activityReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(activityReq.getName() == null || activityReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }

        if(activityReq.getEmail() && activityReq.getEmailTemplateId() == null){
            throw new BadRequestException("Email template cannot be empty");
        }
    }
}
