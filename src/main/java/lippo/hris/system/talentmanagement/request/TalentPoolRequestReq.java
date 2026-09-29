package lippo.hris.system.talentmanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolRequestReq {
    private String positionCode;
    private String positionName;
    private String pilarName;
    private String buName;
    private List<TalentPoolRequestDetailReq> talents;
}
