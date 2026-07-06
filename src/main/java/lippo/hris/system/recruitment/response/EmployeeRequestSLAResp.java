package lippo.hris.system.recruitment.response;

public interface EmployeeRequestSLAResp {

    String getName();
    Integer getSLA();
    Integer getHiringDays();
    Integer getSourcingInProgress();
    Integer getInterviewHRInProgress();
    Integer getInterviewUserInProgress();
    Integer getPsikotesInProgress();
    Integer getOfferingInProgress();
    Integer getSourcingDays();
    Integer getInterviewHRDays();
    Integer getInterviewUserDays();
    Integer getPsikotesDays();
    Integer getOfferingDays();
}
