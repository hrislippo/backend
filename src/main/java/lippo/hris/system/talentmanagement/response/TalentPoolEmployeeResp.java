package lippo.hris.system.talentmanagement.response;

import lombok.Data;

@Data
public class TalentPoolEmployeeResp {
    private String employeeNIK;
    private String readiness;
    private Integer performance;
    private Integer potential;
}
