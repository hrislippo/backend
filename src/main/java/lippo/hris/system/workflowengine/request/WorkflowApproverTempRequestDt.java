package lippo.hris.system.workflowengine.request;

import lombok.Data;

@Data
public class WorkflowApproverTempRequestDt {
    private String organizationCode;
    private String organizationName;
    private String jobLevelCode;
    private String jobLevelName;
    private String companyCode;
    private String companyName;
    private String locationCode;
    private String locationName;
    private String positionCode;
    private String positionName;
    private String jobTitleCode;
    private String jobTitleName;
    private String employeeNik;
    private String employeeName;
}
