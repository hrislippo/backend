package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.PositionLevelReq;
import org.springframework.stereotype.Component;

@Component
public class PositionLevelValidation {

    public void addPositionLevelRequired(PositionLevelReq positionLevelReq) {
        if(positionLevelReq.getCode() == null || positionLevelReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(positionLevelReq.getName() == null || positionLevelReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
