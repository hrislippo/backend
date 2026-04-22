package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleMassEmployeeDetailReq {
    private Long employeeCandidateId;
    private LocalDateTime scheduleTime;
}
