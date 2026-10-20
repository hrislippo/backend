package lippo.hris.system.workflowengine.request;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowApproverTempRequest {
    private Long id;
    private String tempCode;
    private String tempName;
    private Boolean active;
    private Boolean flagCompany;
    private Boolean flagOrganization;
    private Boolean flagJobLevel;
    private Boolean flagJobTitle;
    private Boolean flagLocation;
    private Boolean flagPosition;
    private Boolean flagEmployee;
    private List<WorkflowApproverTempRequestDt> detailList;
}
