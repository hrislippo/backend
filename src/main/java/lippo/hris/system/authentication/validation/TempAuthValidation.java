package lippo.hris.system.authentication.validation;

import lippo.hris.system.authentication.repository.TemplateAuthRepository;
import lippo.hris.system.authentication.repository.UserTemplateAuthRepository;
import lippo.hris.system.authentication.request.TempAuthOrgRequest;
import lippo.hris.system.authentication.request.TempAuthRequest;
import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
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

    public void organizationRequired(TempAuthRequest tempAuthRequest) {
        if(tempAuthRequest.getOrganizationList() == null || tempAuthRequest.getOrganizationList().isEmpty()) {
            throw new BadRequestException("Organization is required");
        }
    }

    public void organizationDuplicate(TempAuthRequest tempAuthRequest) {
        List<String> orgCodeList = tempAuthRequest.getOrganizationList().stream().map(TempAuthOrgRequest::getOrganizationCode).toList();
        boolean hasDuplicate = orgCodeList.stream()
                .distinct()
                .count() != orgCodeList.size();

        if(hasDuplicate){
            throw new BadRequestException("Organization cannot duplicate");
        }
    }

    public void templateUsed(Long id) {
        if(!userTemplateAuthRepository.findByTemplateAuth(templateAuthRepository.findById(id).get()).isEmpty()){
            throw new ConflictException("Template is already used");
        }
    }
}
