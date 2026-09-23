package lippo.hris.system.talentmanagement.request;

import lombok.Data;

@Data
public class TalentPoolEmployeeReq {
    private String employeeNIK;
    private String readiness;
    private Integer performance;
    private Integer potential;
}
