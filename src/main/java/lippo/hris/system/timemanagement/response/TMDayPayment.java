package lippo.hris.system.timemanagement.response;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TMDayPayment {

    private Integer DPRightsId;
    private Integer DPCount;
    private Integer DPRealized;
    private LocalDateTime UpdDate;
    private String UpdUser;
    private String UpdFlag;
    private LocalDate DPDate;
    private LocalDate DPExpDate;
    private String DPSource;

}
