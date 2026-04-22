package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class CandidateLanguageReq {
    private Long id;
    private String languageName;
    private String proficiency;
}
