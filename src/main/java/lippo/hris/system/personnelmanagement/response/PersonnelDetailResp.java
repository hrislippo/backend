package lippo.hris.system.personnelmanagement.response;

import lombok.Data;

@Data
public class PersonnelDetailResp {

    private byte[] empPhoto;
    private String empName;
    private String empNIK;
    private String posName;
    private String orgName;
    private String locationName;
    private String compName;
    private String base64EmployeePhoto;
}
