package lippo.hris.system.talentmanagement.response;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolRequestDetailResp {

    private String positionName;
    private String pilarName;
    private String buName;
    private String employeeNik;
    private String employeeName;
    private List<TalentPoolRequestEmployeeResp> talents;
}
