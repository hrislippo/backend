package lippo.hris.system.recruitment.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequestKanbanResp {
    private String name;
    private List<String> stage;
    private List<EmployeeRequestKanban> employeeStage;
}
