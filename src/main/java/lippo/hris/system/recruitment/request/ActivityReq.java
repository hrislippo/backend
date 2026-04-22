package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class ActivityReq {

    private Long id;
    private String group;
    private String code;
    private String name;
    private Long emailTemplateId;
    private Boolean interview;
    private Boolean email;
}
