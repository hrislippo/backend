package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewReq {
    private Long id;
    private Long candidateId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
