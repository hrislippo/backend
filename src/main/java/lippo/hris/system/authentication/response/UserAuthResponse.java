package lippo.hris.system.authentication.response;

import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.organization.entity.HRBP;
import lippo.hris.system.recruitment.entity.CandidatePool;
import lombok.Data;

import java.util.List;

@Data
public class UserAuthResponse {

    private List<TemplateAuth> templateAuthList;
    private List<CandidatePool> candidatePoolList;
    private List<HRBP> hrbpAuthList;
}
