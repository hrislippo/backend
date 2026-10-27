package lippo.hris.system.workflowengine.response;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempResponse {
    private String tempCode;
    private String tempName;
    private WorkflowTempApproveeResponse approveeList;
    private List<WorkflowTempApproverResponse> approverList;
}
