package lippo.hris.system.recruitment.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface EmployeeRequestResp {
    Long getId();
    String getCode();
    String getName();
    String getBusinessUnitName();
    String getHRBPName();
    Integer getRequestNumber();
    Integer getRecruitNumber();
    String getStatus();
    Boolean getEligible();
    String getStage();

    @JsonFormat(pattern = "dd MMMM yyyy")
    LocalDate getStartDate();

    @JsonFormat(pattern = "dd MMMM yyyy")
    LocalDate getExpDate();
}
