package lippo.hris.system.workflowengine.response;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempApproverResponse {
    private Integer level;
    private List<WorkflowTempJobTtlResponse> jobTtlList;
    private List<WorkflowTempPosResponse> posList;
    private List<WorkflowTempEmpResponse> empList;
    private List<WorkflowTempCompResponse> compList;
    private List<WorkflowTempOrgResponse> orgList;
    private List<WorkflowTempJobLvlResponse> jobLvlList;
    private List<WorkflowTempLocResponse> locList;
    private List<WorkflowTempOrgChartResponse> orgChartList;
}
