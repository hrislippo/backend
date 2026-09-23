package lippo.hris.system.talentmanagement.response;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolDetailResp {
    private String positionCode;
    private String positionName;
    private List<TalentPoolEmployeeResp> talentPoolEmployee;
}
