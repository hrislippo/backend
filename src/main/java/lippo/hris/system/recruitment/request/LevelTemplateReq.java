package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.util.List;

@Data
public class LevelTemplateReq {
    private Long id;
    private String code;
    private String name;
    private Integer days;
}
