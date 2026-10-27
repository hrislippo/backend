package lippo.hris.system.workflowengine.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowTempLocResponse {
    private String locCode;
    private String locName;
}
