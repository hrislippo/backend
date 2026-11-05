package lippo.hris.system.timemanagement.response;

import lippo.hris.system.timemanagement.entity.MobileAttendanceGroupTemplate;
import lombok.Data;

import java.util.List;

@Data
public class MobileAttendanceGroupTempDtResp {
    MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate;
    List<MobileAttendanceGroupTemplateDtResp> templateList;
}
