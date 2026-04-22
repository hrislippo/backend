package lippo.hris.system.emailengine.request;

import lombok.Data;

@Data
public class EmailTemplateReq {
    private String code;
    private String name;
    private String subject;
    private String contentHtml;
    private String designJson;
}
