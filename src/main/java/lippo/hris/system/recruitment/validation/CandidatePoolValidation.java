package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.CandidatePoolReq;
import org.springframework.stereotype.Component;

@Component
public class CandidatePoolValidation {

    public void addCandidatePoolRequired(CandidatePoolReq candidatePoolReq) {
        if(candidatePoolReq.getCode() == null || candidatePoolReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(candidatePoolReq.getName() == null || candidatePoolReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
