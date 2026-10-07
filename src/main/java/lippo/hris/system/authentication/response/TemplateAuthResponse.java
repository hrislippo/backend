package lippo.hris.system.authentication.response;

import lombok.Data;

import java.util.List;

@Data
public class TemplateAuthResponse {
    private String templateCode;
    private String templateName;
    private List<TemplateAuthOrgResponse> organization;
}
