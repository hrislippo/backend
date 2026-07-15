package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeRequestReq {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate expDate;
    private Long businessUnitId;
    private Long hrbpId;
    private List<Long> pic;
    private List<Long> candidate;
    private Long template;
    private Long templateLevel;
    private Integer requestNumber;
    private String hiringName;
    private String hiringPositionName;
    private String hiringCompanyName;
    private String hiringDivisionName;
    private String reportTo;
    private Long employmentType;
    private String requisitionType;
    private String replacementEmployee;
    private Long positionLevel;
    private Integer workingDays;
    private String jobDescription;
    private String educationBackground;
    private String minimumExperience;
    private String skillRequirement;
    private String reason;
    private String resumeType;
}
