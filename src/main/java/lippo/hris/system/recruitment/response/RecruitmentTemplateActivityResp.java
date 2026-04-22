package lippo.hris.system.recruitment.response;

import lombok.Data;

import java.util.List;

@Data
public class RecruitmentTemplateActivityResp {
    private String code;
    private String name;
    private List<RecruitmentActivityTemplateResp> activities;
}
