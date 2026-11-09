package lippo.hris.system.recruitment.response;

public interface CandidateResp {

    Long getCanId();
    String getCanName();
    String getCanPhone();
    String getCanEmail();
    Boolean getCanFgShortlist();
    Boolean getCanFgBlacklist();
}
