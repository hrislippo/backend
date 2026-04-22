package lippo.hris.system.recruitment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeRequestKanbanDetail {
    private Long id;
    private String activityName;
    private String status;
    private Boolean interview;
    private Boolean email;
    private String interviewNotes;

    @JsonFormat(pattern = "dd MMMM yyyy HH:mm")
    private LocalDateTime scheduleTime;
}
