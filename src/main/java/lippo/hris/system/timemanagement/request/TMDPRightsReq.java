package lippo.hris.system.timemanagement.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TMDPRightsReq {

    private String nikEmp;
    private String nikEmpCreate;
    private Integer dpCount;
    private LocalDate dpDate;
    private LocalDate dpExpiredDate;
    private String description;
}
