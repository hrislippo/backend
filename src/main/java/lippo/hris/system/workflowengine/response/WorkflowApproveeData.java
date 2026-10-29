package lippo.hris.system.workflowengine.response;

import lombok.Data;

@Data
public class WorkflowApproveeData {
    private String compCode;
    private String orgCode;
    private String jobLvlCode;
    private String jobTtlCode;
    private String locationCode;
    private String posCode;
    private String empNIK;
}
