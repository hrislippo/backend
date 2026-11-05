package lippo.hris.system.timemanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class MobileAttendanceGroupTemplateReq {
    private Long id;
    private String code;
    private String name;
    private Long hrbpId;
    private List<MobileAttendanceGroupTemplateDtReq> detailTemplates;
}
