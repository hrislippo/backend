package lippo.hris.system.workflowengine.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface WorkflowTransResponse {

    @JsonFormat(pattern = "dd MMMM yyyy")
    LocalDate getApplyDate();

    Long getId();
    Long getApproverId();
    String getTransactionCode();
    String getDocumentType();
    String getStatus();
}
