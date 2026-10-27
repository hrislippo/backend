package lippo.hris.system.workflowengine.request;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempRequest {
    private Long id;
    private Long groupTempId;
    private String tempCode;
    private String tempName;
    private Boolean active;
    private List<TempApproveeRequestDt> approveeList;
    private List<TempApproverRequestDt> approverList;
}
