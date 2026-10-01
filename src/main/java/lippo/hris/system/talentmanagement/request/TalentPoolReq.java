package lippo.hris.system.talentmanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolReq {

    private String employeeNIK;
    private String employeeName;
    private String positionName;
    private String organizationName;
    private String locationName;
    private String companyName;
    private String reason;
    private Long performance;
    private Long potential;
}
