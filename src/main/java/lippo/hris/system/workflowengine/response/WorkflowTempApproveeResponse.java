package lippo.hris.system.workflowengine.response;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempApproveeResponse {
    private List<WorkflowTempOrgResponse> orgList;
    private List<WorkflowTempJobLvlResponse> jobLvlList;
    private List<WorkflowTempCompResponse> compList;
    private List<WorkflowTempLocResponse> locList;
    private List<WorkflowTempPosResponse> posList;
    private List<WorkflowTempJobTtlResponse> jobTtlList;
    private List<WorkflowTempEmpResponse> empList;
}
