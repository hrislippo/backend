package lippo.hris.system.authentication.response;

import lombok.Data;

@Data
public class TemplateAuthDtResponse {
    private String orgCode;
    private String orgName;
    private String jobLvlCode;
    private String jobLvlName;
    private String compCode;
    private String compName;
    private String locCode;
    private String locName;
    private String posCode;
    private String posName;
    private String jobTtlCode;
    private String jobTtlName;
}
