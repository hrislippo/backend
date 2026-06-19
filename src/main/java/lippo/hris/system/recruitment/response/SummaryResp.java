package lippo.hris.system.recruitment.response;

import lombok.Data;

@Data
public class SummaryResp {

    private Integer totalVacancies;
    private Integer totalCandidates;
    private Integer totalInterviews;
    private Integer totalHired;
    private Integer totalAssessment;
    private Integer totalOffering;
    private Integer totalBackgroundCheck;
    private Integer totalSignAgreement;
    private Integer totalOnboarding;
    private Integer totalPipeline;
}
