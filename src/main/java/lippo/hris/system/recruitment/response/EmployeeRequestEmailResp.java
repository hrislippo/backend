package lippo.hris.system.recruitment.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequestEmailResp {
    private List<String> emailTo;
    private String subject;
    private String body;
    private String designJson;
}
