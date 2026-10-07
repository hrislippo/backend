package lippo.hris.system.authentication.request;

import lombok.Data;

@Data
public class TempAuthOrgRequest {
    private String organizationCode;
    private String organizationName;
}
