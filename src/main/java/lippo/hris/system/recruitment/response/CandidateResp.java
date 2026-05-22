package lippo.hris.system.recruitment.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface CandidateResp {

    Long getCanId();
    String getCanName();
    String getCanPhone();
    String getCanEmail();
    Boolean getCanFgShortlist();
    Boolean getEligible();
}
