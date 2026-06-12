package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.LevelTemplateReq;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentLevelTemplateValidation {

    public void addRecruitmentLevelTemplateRequired(LevelTemplateReq levelTemplateReq) {
        if(levelTemplateReq.getCode() == null || levelTemplateReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(levelTemplateReq.getName() == null || levelTemplateReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }

        if(levelTemplateReq.getDays() == null || levelTemplateReq.getDays() <= 0){
            throw new BadRequestException("Days must be greater than 0");
        }

    }
}
