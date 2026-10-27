package lippo.hris.system.workflowengine.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowTempJobTtlResponse {
    private String jobTtlCode;
    private String jobTtlName;
}
