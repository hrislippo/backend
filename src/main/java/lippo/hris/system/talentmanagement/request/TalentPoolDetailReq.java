package lippo.hris.system.talentmanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolDetailReq {
    private String positionCode;
    private String positionName;
    private List<TalentPoolEmployeeReq> employeeList;
}
