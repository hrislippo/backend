package lippo.hris.system.recruitment.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface InterviewCanResp {
    String getInterviewName();

    @JsonFormat(pattern = "dd MMMM yyyy")
    LocalDateTime getInterviewDate();

    String getInterviewNotes();
}
