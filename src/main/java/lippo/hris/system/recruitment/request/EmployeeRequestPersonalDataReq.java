package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestPersonalDataReq {
    private Long resultId;
    private String token;
    private String name;
    private String ktpNumber;
    private LocalDate birthDate;
    private String gender;
    private String maritalStatus;
    private String religion;
    private String address;
    private String phoneNumber;
    private String email;
}
