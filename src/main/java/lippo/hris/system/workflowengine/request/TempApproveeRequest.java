package lippo.hris.system.workflowengine.request;

import lombok.Data;

import java.util.List;

@Data
public class TempApproveeRequest {
    private Long tempId;
    private List<TempApproveeRequestDt> detailList;
}
