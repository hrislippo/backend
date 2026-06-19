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
            value = "WITH LastSixMonths AS ( " +
                    "    SELECT DATEADD(MONTH, -5, DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1)) AS month_start " +
                    "    UNION ALL " +
                    "    SELECT DATEADD(MONTH, 1, month_start) " +
                    "    FROM LastSixMonths " +
                    "    WHERE month_start < DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1) " +
                    ") " +
                    "SELECT LEFT(DATENAME(MONTH, m.month_start), 3) AS month, COUNT(a.createdDate) AS applications " +
                    "FROM LastSixMonths m " +
                    "LEFT JOIN RCMEmpReqCandidate a " +
                    "ON YEAR(a.createdDate) = YEAR(m.month_start) AND MONTH(a.createdDate) = MONTH(m.month_start) " +
                    "GROUP BY m.month_start " +
                    "ORDER BY m.month_start " +
                    "OPTION (MAXRECURSION 6)")
    List<ApplicationResp> getEmployeeRequestCandidateLastSixMonths();

    @Query(nativeQuery = true,
            value = "SELECT TOP(:limit) req.EmpReqName AS name, COUNT(c.EmpReqCanId) AS applicant " +
                    "FROM RCMEmpReqCandidate c " +
                    "LEFT JOIN RCMEmpRequest req ON c.EmpReqId = req.EmpReqId " +
                    "GROUP BY req.EmpReqName, req.createdDate " +
                    "ORDER BY req.createdDate DESC")
    List<JobOpeningResp> getRecentEmployeeRequestWithLimit(@Param("limit") Integer limit);
}
