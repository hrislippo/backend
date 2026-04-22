package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleEmployeeReq {
    private Long id;
    private LocalDateTime scheduleTime;
    private String status;
}
