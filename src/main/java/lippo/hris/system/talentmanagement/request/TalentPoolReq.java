package lippo.hris.system.talentmanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolReq {

    private String employeeNIK;
    private String employeeName;
    private String reason;
    private Long performance;
    private Long potential;
}
