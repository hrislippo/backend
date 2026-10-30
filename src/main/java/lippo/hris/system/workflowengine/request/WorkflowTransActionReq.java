package lippo.hris.system.workflowengine.request;

import lombok.Data;

@Data
public class WorkflowTransActionReq {

    private Long approverId;
    private String action;
    private String description;
}
