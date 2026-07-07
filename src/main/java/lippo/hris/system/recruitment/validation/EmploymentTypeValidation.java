package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.EmploymentTypeReq;
import org.springframework.stereotype.Component;

@Component
public class EmploymentTypeValidation {

    public void addEmploymentTypeRequired(EmploymentTypeReq employmentTypeReq) {
        if(employmentTypeReq.getCode() == null || employmentTypeReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(employmentTypeReq.getName() == null || employmentTypeReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
