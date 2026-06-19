package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormActivityStatus;
import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormStatus;
import lippo.hris.system.recruitment.enumeration.RecruitmentGroupActivity;
import lippo.hris.system.recruitment.repository.*;
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
        summaryResp.setTotalAssessment(getTotalAssessment());
        summaryResp.setTotalOffering(getTotalOffering());
        summaryResp.setTotalBackgroundCheck(getTotalBackgroundCheck());
        summaryResp.setTotalSignAgreement(getTotalSignAgreement());
        summaryResp.setTotalOnboarding(getTotalOnboarding());
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

    private Integer getTotalAssessment(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByCategoryAndStatus
                (RecruitmentGroupActivity.ASSESSMENT.getLabel(), EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
    }

    private Integer getTotalOffering(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByCategoryAndStatus
                (RecruitmentGroupActivity.OFFERING.getLabel(), EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
    }

    private Integer getTotalBackgroundCheck(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByCategoryAndStatus
                (RecruitmentGroupActivity.BACKGROUND_CHECK.getLabel(), EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
    }

    private Integer getTotalSignAgreement(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByCategoryAndStatus
                (RecruitmentGroupActivity.SIGN_AGREEMENT.getLabel(), EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
    }

    private Integer getTotalOnboarding(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByCategoryAndStatus
                (RecruitmentGroupActivity.ONBOARDING.getLabel(), EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
    }
}
