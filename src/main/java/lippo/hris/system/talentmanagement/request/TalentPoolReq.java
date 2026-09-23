package lippo.hris.system.talentmanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class TalentPoolReq {

    private String positionCode;
    private String positionName;
    private List<String> employeeNIK;
}
