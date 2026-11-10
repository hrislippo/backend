package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import lippo.hris.system.recruitment.response.EmployeeRequestSyncResp;
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
                    "WHERE (NOT EXISTS (SELECT 1 FROM URMUserHRBP uh INNER JOIN URMUser u ON uh.UserId = u.UserId WHERE u.UserName = :username) " +
                    "OR h.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username)))")
    Integer countAllHired(@Param("username") String username);

    @Query(nativeQuery = true,
            value = "SELECT can.CanName AS candidateName, req.EmpReqName AS employeeRequestName, " +
                    "bu.RcmBsUnitName AS businessUnitName, " +
                    "CASE WHEN res.EmpReqResFgSync = 1 THEN 'SYNC' ELSE 'NOT_SYNC' END AS syncStatus " +
                    "FROM RCMEmpReqResult res " +
                    "INNER JOIN RCMCANCanData can ON res.CanId = can.CanId " +
                    "INNER JOIN RCMEmpRequest req ON res.EmpReqId = req.EmpReqId " +
                    "INNER JOIN RCMBusinessUnit bu ON req.RcmBsUnitId = bu.RcmBsUnitId " +
                    "WHERE (:canName IS NULL OR can.CanName LIKE '%'+:canName+'%') " +
                    "AND (:reqName IS NULL OR req.EmpReqName LIKE '%'+:reqName+'%') " +
                    "AND (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:sync IS NULL OR res.EmpReqResFgSync = :sync)")
    List<EmployeeRequestSyncResp> getEmployeeRequestSyncResult(@Param("canName") String canName, @Param("reqName") String reqName, @Param("buName") String buName, @Param("sync") Boolean sync);
}

