package lippo.hris.system.workflowengine.request;

import lombok.Data;

@Data
public class WorkflowTempApproverRequestDt {
    private Long approverTemplateId;
    private Integer level;
    private Integer sequence;
}
