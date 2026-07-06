package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.entity.Interview;
import lippo.hris.system.recruitment.response.InterviewCanResp;
import lippo.hris.system.recruitment.response.InterviewResp;
import lippo.hris.system.recruitment.response.UpcomingInterviewResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Interview findByEmployeeRequestCandidateActivity(EmployeeRequestCandidateActivity employeeRequestCandidateActivity);
    Integer countByEmployeeRequestCandidateActivityIsNotNull();
    Integer countByCreatedByAndEmployeeRequestCandidateActivityIsNotNull(String createdBy);

    @Query(nativeQuery = true,
            value = "SELECT iv.CanInterviewId AS id, dt.CanNum, dt.CanName, iv.StartTime, iv.EndTime " +
                    "FROM RCMCANCanData dt " +
                    "JOIN RCMCANCanInterview iv ON dt.CanId = iv.CanId",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMCANCanData dt " +
                    "JOIN RCMCANCanInterview iv ON dt.CanId = iv.CanId")
    Page<InterviewResp> getInterview(Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT CASE WHEN a.RcmActName IS NULL THEN 'Interview Shortlist' " +
                    "ELSE a.RcmActName + ' (' + req.EmpReqName + ')' END AS interviewName, " +
                    "int.StartTime AS interviewDate, int.CanInterviewNotes AS interviewNotes " +
                    "FROM RCMCANCanInterview int " +
                    "LEFT JOIN RCMEmpReqCanActivity act ON int.EmpReqCanActId = act.EmpReqCanActId " +
                    "LEFT JOIN RCMEmpReqCandidate can ON act.EmpReqCanId = can.EmpReqCanId " +
                    "LEFT JOIN RCMEmpRequest req ON can.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMACTActivity a ON act.RcmActId = a.RcmActId " +
                    "WHERE int.CanId = :candidateId")
    List<InterviewCanResp> getInterviewByCandidate(@Param("candidateId") Long candidateId);

    @Query(nativeQuery = true,
            value = "SELECT TOP (:limit) d.CanName AS candidateName, r.EmpReqName AS requestName, a.EmpReqCanActSchedule AS interviewTime " +
                    "FROM RCMCANCanInterview i " +
                    "INNER JOIN RCMEmpReqCanActivity a ON i.EmpReqCanActId = a.EmpReqCanActId " +
                    "INNER JOIN RCMEmpReqCandidate c ON a.EmpReqCanId = c.EmpReqCanId " +
                    "INNER JOIN RCMCANCanData d ON c.CanId = d.CanId " +
                    "INNER JOIN RCMEmpRequest r ON c.EmpReqId = r.EmpReqId " +
                    "WHERE i.StartTime IS NULL AND a.EmpReqCanActSchedule > GETDATE() " +
                    "ORDER BY a.EmpReqCanActSchedule")
    List<UpcomingInterviewResp> getUpcomingInterview(@Param("limit") Integer limit);

    @Query(nativeQuery = true,
            value = "SELECT TOP (:limit) d.CanName AS candidateName, r.EmpReqName AS requestName, a.EmpReqCanActSchedule AS interviewTime " +
                    "FROM RCMCANCanInterview i " +
                    "INNER JOIN RCMEmpReqCanActivity a ON i.EmpReqCanActId = a.EmpReqCanActId " +
                    "INNER JOIN RCMEmpReqCandidate c ON a.EmpReqCanId = c.EmpReqCanId " +
                    "INNER JOIN RCMCANCanData d ON c.CanId = d.CanId " +
                    "INNER JOIN RCMEmpRequest r ON c.EmpReqId = r.EmpReqId " +
                    "INNER JOIN RCMEmpReqPIC pic ON r.EmpReqId = pic.EmpReqId " +
                    "INNER JOIN URMUser usr ON pic.UserId = usr.UserId " +
                    "WHERE i.StartTime IS NULL AND a.EmpReqCanActSchedule > GETDATE() " +
                    "AND usr.UserName = :nik " +
                    "ORDER BY a.EmpReqCanActSchedule")
    List<UpcomingInterviewResp> getUpcomingInterviewByUsername(@Param("limit") Integer limit, @Param("nik") String username);
}
