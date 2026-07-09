package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestResultRepository extends JpaRepository<EmployeeRequestResult, Long> {
    List<EmployeeRequestResult> findByEmployeeRequestForm(EmployeeRequestForm employeeRequestForm);
    Integer countByCreatedBy(String createdBy);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(1) " +
                    "FROM RCMEmpReqResult res " +
                    "INNER JOIN RCMEmpRequest req ON res.EmpReqId = req.EmpReqId " +
                    "INNER JOIN RCMHRBP h ON req.RcmHRBPId = h.RcmHRBPId " +
                    "WHERE (:flagHRBP = CAST(0 AS BIT) OR h.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username)))")
    Integer countAllHired(@Param("username") String username,
                          @Param("flagHRBP") Boolean flagHRBP);
}
