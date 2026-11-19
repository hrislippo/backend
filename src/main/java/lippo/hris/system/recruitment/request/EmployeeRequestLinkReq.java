package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestLinkReq {
    private Long resultId;
    private LocalDate startDate;
    private LocalDate endDate;
}
