package lippo.hris.system.workflowengine.response;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowTempResponse {
    private String tempCode;
    private String tempName;
    private Boolean flagCompany;
    private Boolean flagOrganization;
    private Boolean flagJobLevel;
    private Boolean flagJobTitle;
    private Boolean flagLocation;
    private Boolean flagPosition;
    private Boolean flagEmployee;
    private List<WorkflowTempDtResponse> detailList;
}
