package lippo.hris.system.workflowengine.response;

import lombok.Data;

@Data
public class WorkflowTempDtResponse {
    private String orgCode;
    private String orgName;
    private String jobLvlCode;
    private String jobLvlName;
    private String compCode;
    private String compName;
    private String locCode;
    private String locName;
    private String posCode;
    private String posName;
    private String jobTtlCode;
    private String jobTtlName;
    private String empNik;
    private String empName;
}
