package lippo.hris.system.authentication.validation;

import lippo.hris.system.authentication.repository.TemplateAuthRepository;
import lippo.hris.system.authentication.repository.UserTemplateAuthRepository;
import lippo.hris.system.authentication.request.TempAuthDtRequest;
import lippo.hris.system.authentication.request.TempAuthRequest;
import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TempAuthValidation {

    @Autowired
    UserTemplateAuthRepository userTemplateAuthRepository;

    @Autowired
    TemplateAuthRepository templateAuthRepository;

    public void codeNameRequired(TempAuthRequest tempAuthRequest) {
        if(tempAuthRequest.getTempAuthCode() == null || tempAuthRequest.getTempAuthCode().isEmpty()) {
            throw new BadRequestException("Template code is required");
        }

        if(tempAuthRequest.getTempAuthName() == null || tempAuthRequest.getTempAuthName().isEmpty()) {
            throw new BadRequestException("Template name is required");
        }
    }

    public void detailRequired(TempAuthRequest tempAuthRequest) {
        if(tempAuthRequest.getDetailList() == null || tempAuthRequest.getDetailList().isEmpty()) {
            throw new BadRequestException("Detail is required");
        }
    }

    public void detailDuplicate(TempAuthRequest tempAuthRequest) {
        boolean hasDuplicate = tempAuthRequest.getDetailList().stream()
                .distinct()
                .count() != tempAuthRequest.getDetailList().size();

        if(hasDuplicate){
            throw new BadRequestException("Detail cannot duplicate");
        }
    }

    public void templateUsed(Long id) {
        if(!userTemplateAuthRepository.findByTemplateAuth(templateAuthRepository.findById(id).get()).isEmpty()){
            throw new ConflictException("Template is already used");
        }
    }
}
