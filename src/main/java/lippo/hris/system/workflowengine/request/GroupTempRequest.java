package lippo.hris.system.workflowengine.request;

import lombok.Data;

@Data
public class GroupTempRequest {
    private String groupTempCode;
    private String groupTempName;
    private String docTypeCode;
}
