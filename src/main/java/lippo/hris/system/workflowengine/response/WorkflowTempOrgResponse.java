package lippo.hris.system.workflowengine.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowTempOrgResponse {
    private String orgCode;
    private String orgName;
}
