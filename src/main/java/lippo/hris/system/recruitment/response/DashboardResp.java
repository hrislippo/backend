package lippo.hris.system.recruitment.response;

import lombok.Data;

import java.util.List;

@Data
public class DashboardResp {

    private SummaryResp summary;
    private List<ApplicationResp> applications;
    private List<JobOpeningResp> jobOpenings;
    private List<UpcomingInterviewResp> interviews;
}
