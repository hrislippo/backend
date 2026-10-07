package lippo.hris.system.authentication.request;

import lombok.Data;

import java.util.List;

@Data
public class TempAuthRequest {
    private Long id;
    private String tempAuthCode;
    private String tempAuthName;
    private List<TempAuthOrgRequest> organizationList;
}
