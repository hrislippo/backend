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

import java.util.List;

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

    public DashboardResp getDashboard(String username, List<String> roles){
        SummaryResp summaryResp = new SummaryResp();
        summaryResp.setTotalVacancies(getTotalVacancies(username));
        summaryResp.setTotalCandidates(getTotalCandidates(username));
        summaryResp.setTotalInterviews(getTotalInterviews(username));
        summaryResp.setTotalOffered(getTotalOffered(username));
        summaryResp.setTotalHired(getTotalHired(username));

        DashboardPipelineResp dashboardPipelineResp = getTotalPipeline(username);
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
        dashboardResp.setApplications(employeeRequestCandidateRepository.getEmployeeRequestCandidateLastSixMonths(username));
        dashboardResp.setJobOpenings(employeeRequestCandidateRepository.getRecentEmployeeRequestWithLimit(3, username));
        dashboardResp.setInterviews(interviewRepository.getUpcomingInterview(3, username));
        dashboardResp.setEmployeeRequestSLA(employeeRequestFormRepository.getEmployeeRequestSLA(username));
        return dashboardResp;
    }

    public DashboardResp getDashboardPersonal(String username){
        SummaryResp summaryResp = new SummaryResp();
        summaryResp.setTotalVacancies(getTotalVacanciesPersonal(username));
        summaryResp.setTotalCandidates(getTotalCandidatesPersonal(username));
        summaryResp.setTotalInterviews(getTotalInterviewsPersonal(username));
        summaryResp.setTotalOffered(getTotalOfferedPersonal(username));
        summaryResp.setTotalHired(getTotalHiredPersonal(username));

        DashboardPipelineResp dashboardPipelineResp = getTotalPipelinePersonal(username);
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

    private Integer getTotalVacancies(String username){
        return employeeRequestFormRepository.countOpenRequest(username);
    }

    private Integer getTotalVacanciesPersonal(String username){
        return employeeRequestPICRepository.countByUserAndInProgress(username);
    }

    private Integer getTotalCandidates(String username){
        return employeeRequestCandidateRepository.countTotalCandidate(username);
    }

    private Integer getTotalCandidatesPersonal(String username){
        User user = userRepository.findByusername(username).get();
        return candidateRepository.countByUser(user);
    }

    private Integer getTotalInterviews(String username){
        return interviewRepository.countTotalInterview(username);
    }

    private Integer getTotalInterviewsPersonal(String username){
        return interviewRepository.countByCreatedByAndEmployeeRequestCandidateActivityIsNotNull(username);
    }

    private Integer getTotalOffered(String username){
        return employeeRequestCandidateActivityRepository.getCompletedOfferings(username);
    }

    private Integer getTotalOfferedPersonal(String username){
        return employeeRequestCandidateActivityRepository.getCompletedOfferingsByUsername(username);
    }

    private Integer getTotalHired(String username){
        return employeeRequestResultRepository.countAllHired(username);
    }

    private Integer getTotalHiredPersonal(String username){
        return employeeRequestResultRepository.countByCreatedBy(username);
    }

    private DashboardPipelineResp getTotalPipeline(String username){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipeline(username);
    }

    private DashboardPipelineResp getTotalPipelinePersonal(String username){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByUsername(username);
    }
}
