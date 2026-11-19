package lippo.hris.system.recruitment.response;

public interface EmployeeRequestSyncResp {

    Long getId();
    String getCandidateName();
    String getEmployeeRequestName();
    String getBusinessUnitName();
    String getSyncStatus();
    Boolean getEmployeeData();
    Boolean getPersonalData();
}
