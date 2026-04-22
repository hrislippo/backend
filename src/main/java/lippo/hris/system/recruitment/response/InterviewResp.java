package lippo.hris.system.recruitment.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface InterviewResp {
    Long getId();
    String getCanNum();
    String getCanName();

    @JsonFormat(pattern = "dd MMMM yyyy HH:mm:ss")
    LocalDateTime getStartTime();

    @JsonFormat(pattern = "dd MMMM yyyy HH:mm:ss")
    LocalDateTime getEndTime();
}
