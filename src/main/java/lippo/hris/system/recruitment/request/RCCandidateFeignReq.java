package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RCCandidateFeignReq {
    private String canName;
    private LocalDate birthDate;
    private String gender;
    private String maritalStatus;
    private String religion;
    private String initiatorNik;
    private String resAddress;
    private String oriAddress;
    private String positionCode;
    private String jobLvlCode;
    private String jobTtlCode;
    private String orgCode;
    private String companyCode;
    private String locationCode;
    private String costCenterCode;
    private String empType;
    private LocalDate jobStartDate;
    private LocalDate jobEndDate;
    private String locationRecruitCode;
    private LocalDate effectiveDate;
    private LocalDate startDate;
    private LocalDate joinDate;
    private LocalDate signDate;
    private String ktpNumber;
    private String phoneNumber;
    private String email;
}
