package lippo.hris.system.recruitment.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface UpcomingInterviewResp {

    String getCandidateName();
    String getRequestName();

    @JsonFormat(pattern = "dd MMMM yyyy HH:mm")
    LocalDateTime getInterviewTime();
}
