package lippo.hris.system.workflowengine.request;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempApproverRequest {
    private Long templateId;
    private List<WorkflowTempApproverRequestDt> templateApproverList;
}
