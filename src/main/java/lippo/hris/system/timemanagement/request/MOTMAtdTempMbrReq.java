package lippo.hris.system.timemanagement.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MOTMAtdTempMbrReq {

    private List<String> nikList;
    private String tempCode;
    private String nik;
    private String createdBy;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
