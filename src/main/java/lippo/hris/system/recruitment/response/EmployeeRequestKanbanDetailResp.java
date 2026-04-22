package lippo.hris.system.recruitment.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequestKanbanDetailResp {
    private String name;
    private String erfName;
    private String status;
    private List<EmployeeRequestKanbanDetail> listActivities;
}
