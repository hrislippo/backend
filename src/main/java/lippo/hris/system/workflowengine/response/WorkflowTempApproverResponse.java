package lippo.hris.system.workflowengine.response;

import lippo.hris.system.workflowengine.entity.WorkflowApproverTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprover;
import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempApproverResponse {
    List<WorkflowApproverTemp> sequence;
}
