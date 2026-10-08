package lippo.hris.system.authentication.request;

import lombok.Data;

@Data
public class TempAuthDtRequest {
    private String organizationCode;
    private String organizationName;
    private String jobLevelCode;
    private String jobLevelName;
}
