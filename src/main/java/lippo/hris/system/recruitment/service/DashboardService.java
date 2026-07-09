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
        Boolean flagHRBP = false;
        if(roles.contains("ROLE_HRBP")){
            flagHRBP = true;
        }

        SummaryResp summaryResp = new SummaryResp();
        summaryResp.setTotalVacancies(getTotalVacancies(username, flagHRBP));
        summaryResp.setTotalCandidates(getTotalCandidates(username, flagHRBP));
        summaryResp.setTotalInterviews(getTotalInterviews(username, flagHRBP));
        summaryResp.setTotalOffered(getTotalOffered(username, flagHRBP));
        summaryResp.setTotalHired(getTotalHired(username, flagHRBP));

        DashboardPipelineResp dashboardPipelineResp = getTotalPipeline(username, flagHRBP);
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
        dashboardResp.setApplications(employeeRequestCandidateRepository.getEmployeeRequestCandidateLastSixMonths(username, flagHRBP));
        dashboardResp.setJobOpenings(employeeRequestCandidateRepository.getRecentEmployeeRequestWithLimit(3, username, flagHRBP));
        dashboardResp.setInterviews(interviewRepository.getUpcomingInterview(3, username, flagHRBP));
        dashboardResp.setEmployeeRequestSLA(employeeRequestFormRepository.getEmployeeRequestSLA(username, flagHRBP));
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

    private Integer getTotalVacancies(String username, Boolean flagHRBP){
        return employeeRequestFormRepository.countOpenRequest(username, flagHRBP);
    }

    private Integer getTotalVacancies(String username){
        return employeeRequestPICRepository.countByUserAndInProgress(username);
    }

    private Integer getTotalCandidates(String username, Boolean flagHRBP){
        if(flagHRBP){
            return employeeRequestCandidateRepository.countTotalCandidate(username, flagHRBP);
        }
        return candidateRepository.findAll().size();
    }

    private Integer getTotalCandidates(String username){
        User user = userRepository.findByusername(username).get();
        return candidateRepository.countByUser(user);
    }

    private Integer getTotalInterviews(String username, Boolean flagHRBP){
        return interviewRepository.countTotalInterview(username, flagHRBP);
    }

    private Integer getTotalInterviews(String username){
        return interviewRepository.countByCreatedByAndEmployeeRequestCandidateActivityIsNotNull(username);
    }

    private Integer getTotalOffered(String username, Boolean flagHRBP){
        return employeeRequestCandidateActivityRepository.getCompletedOfferings(username, flagHRBP);
    }

    private Integer getTotalOffered(String username){
        return employeeRequestCandidateActivityRepository.getCompletedOfferingsByUsername(username);
    }

    private Integer getTotalHired(String username, Boolean flagHRBP){
        return employeeRequestResultRepository.countAllHired(username, flagHRBP);
    }

    private Integer getTotalHired(String username){
        return employeeRequestResultRepository.countByCreatedBy(username);
    }

    private DashboardPipelineResp getTotalPipeline(String username, Boolean flagHRBP){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipeline(username, flagHRBP);
    }

    private DashboardPipelineResp getTotalPipeline(String username){
        return employeeRequestCandidateActivityRepository.getEmployeeRequestPipelineByUsername(username);
    }
}
