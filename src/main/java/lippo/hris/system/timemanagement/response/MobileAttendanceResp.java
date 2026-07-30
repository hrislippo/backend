package lippo.hris.system.timemanagement.response;

import java.time.LocalDate;

public interface MobileAttendanceResp {

    Long getId();
    String getEmpNIK();
    String getTempCode();
    LocalDate getStartDate();
    LocalDate getEndDate();
}
