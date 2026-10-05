package lippo.hris.system.timemanagement.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TMDPRightsReq {

    private List<TMDPRightsEmployeeReq> employee;
    private String nikEmpCreate;
    private Integer dpCount;
    private LocalDate dpDate;
    private LocalDate dpExpiredDate;
    private String description;
}
