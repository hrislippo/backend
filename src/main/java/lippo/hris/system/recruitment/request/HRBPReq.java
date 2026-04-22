package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class HRBPReq {
    private Long id;
    private Long businessUnitId;
    private String code;
    private String name;
}
