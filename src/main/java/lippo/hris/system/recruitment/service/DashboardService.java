package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormActivityStatus;
import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormStatus;
import lippo.hris.system.recruitment.enumeration.RecruitmentGroupActivity;
import lippo.hris.system.recruitment.repository.*;
import lippo.hris.system.recruitment.response.DashboardPipelineResp;
import lippo.hris.system.recruitment.response.DashboardResp;
import lippo.hris.system.recruitment.response.SummaryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DashboardService {

    @Autowired
    EmployeeRequestFormRepository employeeRequestFormRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    EmployeeRequestCandidateRepository employeeRequestCandidateRepository;

    @Autowired
    EmployeeRequestResultRepository employeeRequestResultRepository;

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    public DashboardResp getDashboard(){
        SummaryResp summaryResp = new SummaryResp();
        summaryResp.setTotalVacancies(getTotalVacancies());
        summaryResp.setTotalCandidates(getTotalCandidates());
        summaryResp.setTotalInterviews(getTotalInterviews());
        summaryResp.setTotalHired(getTotalHired());

        DashboardPipelineResp dashboardPipelineResp = getTotalPipeline();
        summaryResp.setTotalAssessment(dashboardPipelineResp.getTotalAssessment());
        summaryResp.setTotalOffering(dashboardPipelineResp.getTotalOffering());
        summaryResp.setTotalBackgroundCheck(dashboardPipelineResp.getTotalBackgroundCheck());
        summaryResp.setTotalSignAgreement(dashboardPipelineResp.getTotalSignAgreement());
        summaryResp.setTotalOnboarding(dashboardPipelineResp.getTotalOnboarding());
        summaryResp.setTotalPipeline(
                summaryResp.getTotalAssessment() + summaryResp.getTotalOffering() + summaryResp.getTotalBackgroundCheck() +
                        summaryResp.getTotalSignAgreement() + summaryResp.getTotalOnboarding()
        );

        DashboardResp dashboardResp = new DashboardResp();
        dashboardResp.setSummary(summaryResp);
        dashboardResp.setApplications(employeeRequestCandidateRepository.getEmployeeRequestCandidateLastSixMonths());
        dashboardResp.setJobOpenings(employeeRequestCandidateRepository.getRecentEmployeeRequestWithLimit(3));
        dashboardResp.setInterviews(interviewRepository.getUpcomingInterview(3));
        return dashboardResp;
    }

    private Integer getTotalVacancies(){
        return employeeRequestFormRepository.findByStatus(EmployeeRequestFormStatus.IN_PROGRESS.toString()).size();
    }

    private Integer getTotalCandidates(){
        return candidateRepository.findAll().size();
    }

    private Integer getTotalInterviews(){
        return interviewRepository.findAll().size();
    }

    private Integer getTotalHired(){
        return employeeRequestResultRepository.findAll().size();
    }

    private DashboardPipelineResp getTotalPipeline(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipeline();
    }
}
