package lippo.hris.system.workflowengine.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowTempJobLvlResponse {
    private String jobLvlCode;
    private String jobLvlName;
}
