package lippo.hris.system.authentication.response;

import lombok.Data;

@Data
public class TemplateAuthDtResponse {
    private String orgCode;
    private String orgName;
    private String jobLvlCode;
    private String jobLvlName;
}
