package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.UserRepository;
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

    @Autowired
    EmployeeRequestPICRepository employeeRequestPICRepository;

    @Autowired
    UserRepository userRepository;

    public DashboardResp getDashboard(){
        SummaryResp summaryResp = new SummaryResp();
        summaryResp.setTotalVacancies(getTotalVacancies());
        summaryResp.setTotalCandidates(getTotalCandidates());
        summaryResp.setTotalInterviews(getTotalInterviews());
        summaryResp.setTotalOffered(getTotalOffered());
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
        dashboardResp.setEmployeeRequestSLA(employeeRequestFormRepository.getEmployeeRequestSLA());
        return dashboardResp;
    }

    public DashboardResp getDashboardPersonal(String username){
        SummaryResp summaryResp = new SummaryResp();
        summaryResp.setTotalVacancies(getTotalVacancies(username));
        summaryResp.setTotalCandidates(getTotalCandidates(username));
        summaryResp.setTotalInterviews(getTotalInterviews(username));
        summaryResp.setTotalOffered(getTotalOffered(username));
        summaryResp.setTotalHired(getTotalHired(username));

        DashboardPipelineResp dashboardPipelineResp = getTotalPipeline(username);
        summaryResp.setTotalAssessment(dashboardPipelineResp.getTotalAssessment() == null ? 0 : dashboardPipelineResp.getTotalAssessment());
        summaryResp.setTotalOffering(dashboardPipelineResp.getTotalOffering() == null ? 0 : dashboardPipelineResp.getTotalOffering());
        summaryResp.setTotalBackgroundCheck(dashboardPipelineResp.getTotalBackgroundCheck() == null ? 0 : dashboardPipelineResp.getTotalBackgroundCheck());
        summaryResp.setTotalSignAgreement(dashboardPipelineResp.getTotalSignAgreement() == null ? 0 :  dashboardPipelineResp.getTotalSignAgreement());
        summaryResp.setTotalOnboarding(dashboardPipelineResp.getTotalOnboarding() == null ? 0 : dashboardPipelineResp.getTotalOnboarding());
        summaryResp.setTotalPipeline(
                summaryResp.getTotalAssessment() + summaryResp.getTotalOffering() + summaryResp.getTotalBackgroundCheck() +
                        summaryResp.getTotalSignAgreement() + summaryResp.getTotalOnboarding()
        );

        DashboardResp dashboardResp = new DashboardResp();
        dashboardResp.setSummary(summaryResp);
        dashboardResp.setApplications(employeeRequestCandidateRepository.getEmployeeRequestCandidateLastSixMonthsByUsername(username));
        dashboardResp.setJobOpenings(employeeRequestCandidateRepository.getRecentEmployeeRequestWithLimitAndUsername(3, username));
        dashboardResp.setInterviews(interviewRepository.getUpcomingInterviewByUsername(3, username));
        return dashboardResp;
    }

    private Integer getTotalVacancies(){
        return employeeRequestFormRepository.findByStatus(EmployeeRequestFormStatus.IN_PROGRESS.toString()).size();
    }

    private Integer getTotalVacancies(String username){
        return employeeRequestPICRepository.countByUserAndInProgress(username);
    }

    private Integer getTotalCandidates(){
        return candidateRepository.findAll().size();
    }

    private Integer getTotalCandidates(String username){
        User user = userRepository.findByusername(username).get();
        return candidateRepository.countByUser(user);
    }

    private Integer getTotalInterviews(){
        return interviewRepository.countByEmployeeRequestCandidateActivityIsNotNull();
    }

    private Integer getTotalInterviews(String username){
        return interviewRepository.countByCreatedByAndEmployeeRequestCandidateActivityIsNotNull(username);
    }

    private Integer getTotalOffered(){
        return employeeRequestCandidateActivityRepository.getCompletedOfferings();
    }

    private Integer getTotalOffered(String username){
        return employeeRequestCandidateActivityRepository.getCompletedOfferingsByUsername(username);
    }

    private Integer getTotalHired(){
        return employeeRequestResultRepository.findAll().size();
    }

    private Integer getTotalHired(String username){
        return employeeRequestResultRepository.countByCreatedBy(username);
    }

    private DashboardPipelineResp getTotalPipeline(){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipeline();
    }

    private DashboardPipelineResp getTotalPipeline(String username){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByUsername(username);
    }
}
