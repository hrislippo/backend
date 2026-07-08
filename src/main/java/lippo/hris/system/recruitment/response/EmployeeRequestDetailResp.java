package lippo.hris.system.recruitment.response;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.recruitment.entity.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeRequestDetailResp {
    private String code;
    private String name;
    private BusinessUnit businessUnit;
    private HRBP hrbp;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private List<User> pic;
    private List<Candidate> candidate;
    private RecruitmentTemplate template;
    private RecruitmentLevelTemplate levelTemplate;
    private Integer requestNumber;
    private String hiringName;
    private String hiringPositionName;
    private String hiringCompanyName;
    private String hiringDivisionName;
    private String reportTo;
    private EmploymentType employmentType;
    private String requisitionType;
    private String replacementEmployee;
    private PositionLevel positionLevel;
    private Integer workingDays;
    private String jobDescription;
    private String educationBackground;
    private String minimumExperience;
    private String skillRequirement;
}
