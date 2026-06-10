package lippo.hris.system.recruitment.response;

import lippo.hris.system.recruitment.entity.Venue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleResp {

    private LocalDateTime scheduleDate;
    private String interviewerName;
    private String interviewerPosition;
    private String interviewType;
    private String linkInterview;
    private Venue venue;
}
