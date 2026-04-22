package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestCandidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.RecruitmentActivity;
import lippo.hris.system.recruitment.response.EmployeeRequestKanban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestCandidateActivityRepository extends JpaRepository<EmployeeRequestCandidateActivity, Long> {

    List<EmployeeRequestCandidateActivity> findByEmployeeRequestCandidate (EmployeeRequestCandidate employeeRequestCandidate);
    EmployeeRequestCandidateActivity findByEmployeeRequestCandidateAndRecruitmentActivity (EmployeeRequestCandidate employeeRequestCandidate, RecruitmentActivity recruitmentActivity);
    List<EmployeeRequestCandidateActivity> findByEmployeeRequestCandidateAndStatus (EmployeeRequestCandidate employeeRequestCandidate, String status);
    void deleteAllByEmployeeRequestCandidate (EmployeeRequestCandidate employeeRequestCandidate);
}
