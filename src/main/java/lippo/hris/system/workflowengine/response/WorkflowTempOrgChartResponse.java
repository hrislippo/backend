package lippo.hris.system.workflowengine.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowTempOrgChartResponse {
    private String orgChartCode;
    private String orgChartName;
}
