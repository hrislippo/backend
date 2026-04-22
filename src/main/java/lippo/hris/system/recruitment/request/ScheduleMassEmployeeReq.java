package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleMassEmployeeReq {
    private Long activityId;
    private List<ScheduleMassEmployeeDetailReq> scheduleMassEmployeeDetailReq;
}
