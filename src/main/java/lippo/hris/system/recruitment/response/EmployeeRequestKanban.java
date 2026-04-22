package lippo.hris.system.recruitment.response;

import lombok.Data;

@Data
public class EmployeeRequestKanban {
    private Long id;
    private String name;
    private String stage;
    private String status;
}
