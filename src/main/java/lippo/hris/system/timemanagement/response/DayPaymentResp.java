package lippo.hris.system.timemanagement.response;

import java.time.LocalDate;

public interface DayPaymentResp {

    Long getId();
    String getEmpNIK();
    LocalDate getStartDate();
    LocalDate getExpiryDate();
    Integer getDpCount();
}
