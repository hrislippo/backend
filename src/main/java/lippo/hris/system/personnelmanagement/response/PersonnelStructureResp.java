package lippo.hris.system.personnelmanagement.response;

import lombok.Data;

@Data
public class PersonnelStructureResp {

    private Integer id;
    private String employeeNIK;
    private String positionName;
    private String employeeName;
    private String organizationUnit;
    private Integer reportsToPositionId;
    private Integer hierarchyLevel;
    private byte[] employeePhoto;
    private String base64EmployeePhoto;
}
