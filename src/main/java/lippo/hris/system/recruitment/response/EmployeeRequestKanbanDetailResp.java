package lippo.hris.system.recruitment.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequestKanbanDetailResp {
    private String name;
    private Long candidateId;
    private Boolean candidateBlacklist = false;
    private String status;
    private List<EmployeeRequestKanbanDetail> listActivities;
}
