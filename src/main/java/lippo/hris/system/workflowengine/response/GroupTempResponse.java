package lippo.hris.system.workflowengine.response;

import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lombok.Data;

import java.util.List;

@Data
public class GroupTempResponse {
    private String code;
    private String name;
    private WorkflowDocType workflowDocType;
    private Boolean flagCompany;
    private Boolean flagOrganization;
    private Boolean flagJobLevel;
    private Boolean flagJobTitle;
    private Boolean flagLocation;
    private Boolean flagPosition;
    private Boolean flagEmployee;
    private List<WorkflowTemp> workflowTempList;
}
