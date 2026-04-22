package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeRequestInterviewReq {
    private Long id;
    private LocalDate birthDate;
    private String address;
    private Long currentSalary;
    private Long expectedSalary;
    private String notes;
    private LocalDateTime startTime;
}
