package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class CandidateExperienceReq {
    private Long id;
    private String companyName;
    private String locationName;
    private String positionName;
    private String startMonth;
    private String startYear;
    private String endMonth;
    private String endYear;
    private String description;
}
