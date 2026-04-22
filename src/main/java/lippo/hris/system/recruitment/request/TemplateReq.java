package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.util.List;

@Data
public class TemplateReq {
    private Long id;
    private String code;
    private String name;
    private List<Long> activityIds;
}
