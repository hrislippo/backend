package lippo.hris.system.workflowengine.response;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowApproverData {
    private Integer level;
    private List<WorkflowApproverDetail> approverDetail;
}
