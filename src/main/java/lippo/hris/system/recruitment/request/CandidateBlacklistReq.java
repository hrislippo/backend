package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class CandidateBlacklistReq {

    private Long candidateId;
    private Boolean blacklist;
    private String reason;
    private String source;
}
