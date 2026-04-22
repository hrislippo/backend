package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeRequestReq {
    private Long id;
    private String name;
    private LocalDate expDate;
    private Long businessUnitId;
    private Long hrbpId;
    private List<Long> pic;
    private List<Long> candidate;
    private Long template;
    private Long templateLevel;
    private Integer requestNumber;
}
