package lippo.hris.system.workflowengine.request;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lombok.Data;

@Data
public class WorkflowTransReq {

    private WorkflowDocType workflowDocType;
    private Long docId;
    private User approvee;
}
