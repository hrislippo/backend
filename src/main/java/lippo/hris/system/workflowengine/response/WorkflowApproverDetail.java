package lippo.hris.system.workflowengine.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkflowApproverDetail {
    private String approverNik;
    private String approverName;
    private String approveAction;
    private String approveDescription;

    @JsonFormat(pattern = "dd MMMM yyyy HH:mm")
    private LocalDateTime approveTime;
}
