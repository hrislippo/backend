package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.entity.Interview;
import lippo.hris.system.recruitment.response.InterviewResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Interview findByEmployeeRequestCandidateActivity(EmployeeRequestCandidateActivity employeeRequestCandidateActivity);

    @Query(nativeQuery = true,
            value = "SELECT iv.CanInterviewId AS id, dt.CanNum, dt.CanName, iv.StartTime, iv.EndTime " +
                    "FROM RCMCANCanData dt " +
                    "JOIN RCMCANCanInterview iv ON dt.CanId = iv.CanId",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMCANCanData dt " +
                    "JOIN RCMCANCanInterview iv ON dt.CanId = iv.CanId")
    Page<InterviewResp> getInterview(Pageable pageable);
}
