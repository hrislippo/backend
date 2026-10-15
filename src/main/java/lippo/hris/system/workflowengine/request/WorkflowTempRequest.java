package lippo.hris.system.workflowengine.request;

import lombok.Data;

@Data
public class WorkflowTempRequest {
    private Long id;
    private String tempCode;
    private String tempName;
    private Boolean active;
}
