package lippo.hris.system.leave.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveFeignReq {
    private String employeeNik;
    private String initiatorNik;
    private LocalDate leaveDate;
    private Boolean halfLeave;
}
