package lippo.hris.system.personnelmanagement.response;

import lombok.Data;

@Data
public class PersonnelStructureResp {

    private Integer id;
    private String positionName;
    private String employeeName;
    private String organizationUnit;
    private Integer reportsToPositionId;
    private byte[] employeePhoto;
    private String base64EmployeePhoto;
}
