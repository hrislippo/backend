package lippo.hris.system.organization.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.organization.request.BusinessUnitReq;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitValidation {

    public void addBusinessUnitRequired(BusinessUnitReq businessUnitReq) {
        if(businessUnitReq.getCode() == null || businessUnitReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(businessUnitReq.getName() == null || businessUnitReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
