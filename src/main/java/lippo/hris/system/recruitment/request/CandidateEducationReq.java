package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class CandidateEducationReq {
    private Long id;
    private String institutionName;
    private String degreeName;
    private String startMonth;
    private String startYear;
    private String endMonth;
    private String endYear;
}
