package lippo.hris.system.organization.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.organization.request.HRBPReq;
import org.springframework.stereotype.Component;

@Component
public class HRBPValidation {

    public void addHRBPRequired(HRBPReq hrbpReq){
        if(hrbpReq.getCode() == null || hrbpReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(hrbpReq.getName() == null || hrbpReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
