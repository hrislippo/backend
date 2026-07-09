package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.response.ApplicationResp;
import lippo.hris.system.recruitment.response.JobOpeningResp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestCandidateRepository extends JpaRepository<EmployeeRequestCandidate, Long> {

    List<EmployeeRequestCandidate> findByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    void deleteAllByEmployeeRequestForm (EmployeeRequestForm employeeRequestForm);
    EmployeeRequestCandidate findByEmployeeRequestFormAndCandidate (EmployeeRequestForm employeeRequestForm, Candidate candidate);
    List<EmployeeRequestCandidate> findByCandidate (Candidate candidate);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(1) " +
                    "FROM RCMEmpReqCandidate c " +
                    "INNER JOIN RCMEmpRequest req ON c.EmpReqId = req.EmpReqId " +
                    "INNER JOIN RCMHRBP h ON req.RcmHRBPId = h.RcmHRBPId " +
                    "WHERE (:flagHRBP = CAST(0 AS BIT) OR h.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username)))")
    Integer countTotalCandidate(@Param("username") String username,
                                @Param("flagHRBP") Boolean flagHRBP);

    @Query(nativeQuery = true,
            value = "WITH LastSixMonths AS ( " +
                    "SELECT DATEADD(MONTH, -5, DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1)) AS month_start " +
                    "UNION ALL " +
                    "SELECT DATEADD(MONTH, 1, month_start) " +
                    "FROM LastSixMonths " +
                    "WHERE month_start < DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1)) " +
                    "SELECT LEFT(DATENAME(MONTH, m.month_start), 3) AS month, " +
                    "COUNT(CASE WHEN :flagHRBP = CAST(0 AS BIT) THEN a.createdBy " +
                    "WHEN uh.RcmHRBPId IS NOT NULL THEN a.createdBy END) AS applications " +
                    "FROM LastSixMonths m " +
                    "LEFT JOIN RCMEmpReqCandidate a " +
                    "ON YEAR(a.createdDate) = YEAR(m.month_start) AND MONTH(a.createdDate) = MONTH(m.month_start) " +
                    "LEFT JOIN RCMEmpRequest req ON a.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMHRBP h ON req.RcmHRBPId = h.RcmHRBPId " +
                    "LEFT JOIN URMUserHRBP uh ON uh.RcmHRBPId = h.RcmHRBPId " +
                    "AND uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username) " +
                    "GROUP BY m.month_start " +
                    "ORDER BY m.month_start " +
                    "OPTION (MAXRECURSION 6)")
    List<ApplicationResp> getEmployeeRequestCandidateLastSixMonths(@Param("username") String username,
                                                                   @Param("flagHRBP") Boolean flagHRBP);

    @Query(nativeQuery = true,
            value = "WITH LastSixMonths AS ( " +
                    "    SELECT DATEADD(MONTH, -5, DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1)) AS month_start " +
                    "    UNION ALL " +
                    "    SELECT DATEADD(MONTH, 1, month_start) " +
                    "    FROM LastSixMonths " +
                    "    WHERE month_start < DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1) " +
                    ") " +
                    "SELECT LEFT(DATENAME(MONTH, m.month_start), 3) AS month, " +
                    "COUNT(CASE WHEN usr.UserName = :nik THEN a.EmpReqCanId END) AS applications " +
                    "FROM LastSixMonths m " +
                    "LEFT JOIN RCMEmpReqCandidate a ON YEAR(a.createdDate) = YEAR(m.month_start) AND MONTH(a.createdDate) = MONTH(m.month_start) " +
                    "LEFT JOIN RCMEmpReqPIC p ON a.EmpReqId = p.EmpReqId " +
                    "LEFT JOIN URMUser usr ON p.UserId = usr.UserId " +
                    "GROUP BY m.month_start ORDER BY m.month_start " +
                    "OPTION (MAXRECURSION 6)")
    List<ApplicationResp> getEmployeeRequestCandidateLastSixMonthsByUsername(@Param("nik") String username);

    @Query(nativeQuery = true,
            value = "SELECT TOP(:limit) req.EmpReqName AS name, COUNT(c.EmpReqCanId) AS applicant " +
                    "FROM RCMEmpRequest req " +
                    "LEFT JOIN RCMEmpReqCandidate c ON c.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMHRBP h ON req.RcmHRBPId = h.RcmHRBPId " +
                    "WHERE (:flagHRBP = CAST(0 AS BIT) OR h.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username))) " +
                    "GROUP BY req.EmpReqName, req.createdDate " +
                    "ORDER BY req.createdDate DESC")
    List<JobOpeningResp> getRecentEmployeeRequestWithLimit(@Param("limit") Integer limit,
                                                           @Param("username") String username,
                                                           @Param("flagHRBP") Boolean flagHRBP);

    @Query(nativeQuery = true,
            value = "SELECT TOP(:limit) req.EmpReqName AS name, COUNT(c.EmpReqCanId) AS applicant " +
                    "FROM RCMEmpRequest req " +
                    "LEFT JOIN RCMEmpReqCandidate c ON c.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMEmpReqPIC pic ON req.EmpReqId = pic.EmpReqId " +
                    "LEFT JOIN URMUser usr ON pic.UserId = usr.UserId " +
                    "WHERE usr.UserName = :nik " +
                    "GROUP BY req.EmpReqName, req.createdDate " +
                    "ORDER BY req.createdDate DESC")
    List<JobOpeningResp> getRecentEmployeeRequestWithLimitAndUsername(@Param("limit") Integer limit, @Param("nik") String username);
}
