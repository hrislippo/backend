package lippo.hris.system.recruitment.response;

import lippo.hris.system.recruitment.entity.EmployeeRequestResultPersonalDt;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestResultPersonalResp {

    private String name;
    private String address;
    private LocalDate birthDate;
    private String mobilePhone;
    private String email;
    private EmployeeRequestResultPersonalDt personalData;
}
