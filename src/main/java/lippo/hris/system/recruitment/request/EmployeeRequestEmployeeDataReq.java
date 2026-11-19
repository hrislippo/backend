package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestEmployeeDataReq {
    private Long resultId;
    private LocalDate startDate;
    private LocalDate signDate;
    private LocalDate joinDate;
    private String compCode;
    private String compName;
    private String posCode;
    private String posName;
    private String orgCode;
    private String orgName;
    private String jobTtlCode;
    private String jobTtlName;
    private String jobLvlCode;
    private String jobLvlName;
    private String locCode;
    private String locName;
    private String costCenterCode;
    private String costCenterName;
    private String employmentType;
    private Integer employmentTypeMonth;
    private String locRecruitCode;
    private String locRecruitName;
    private LocalDate jobStartDate;
    private LocalDate jobEndDate;
}
