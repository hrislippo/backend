package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.response.EmployeeRequestCandidateResp;
import lippo.hris.system.recruitment.response.EmployeeRequestResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestFormRepository extends JpaRepository<EmployeeRequestForm, Long> {

    @Query(nativeQuery = true,
    value = "SELECT TOP 1 REPLACE(EmpReqCode, :prefix, '') FROM RCMEmpRequest ORDER BY EmpReqCode DESC")
    Long countByEmpReqCodeStartingWith(@Param("prefix") String prefix);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(res.EmpReqResId) AS recruitNumber, " +
                    "req.EmpReqId AS id, req.EmpReqCode AS code, req.EmpReqName AS name, " +
                    "bu.RcmBsUnitName AS businessUnitName, hrbp.RcmHRBPName AS hrbpName, " +
                    "req.EmpReqExpDate AS expDate, req.EmpReqNum AS requestNumber, " +
                    "req.EmpReqStatus AS status, " +
                    "CASE WHEN pic.EmpReqPICId IS NOT NULL THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS eligible " +
                    "FROM RCMEmpRequest req " +
                    "LEFT JOIN RCMEmpReqResult res ON req.EmpReqId = res.EmpReqId " +
                    "LEFT JOIN RCMEmpReqPIC pic ON pic.EmpReqId = req.EmpReqId " +
                    "AND pic.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username) " +
                    "INNER JOIN RCMBusinessUnit bu ON req.RcmBsUnitId = bu.RcmBsUnitId " +
                    "INNER JOIN RCMHRBP hrbp ON req.RcmHRBPId = hrbp.RcmHRBPId " +
                    "WHERE (:code IS NULL OR req.EmpReqCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR req.EmpReqName LIKE '%'+:name+'%') " +
                    "AND (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:hrbpName IS NULL OR hrbp.RcmHRBPName LIKE '%'+:hrbpName+'%') " +
                    "GROUP BY req.EmpReqId, req.EmpReqCode, req.EmpReqName, " +
                    "bu.RcmBsUnitName, hrbp.RcmHRBPName, req.EmpReqExpDate, req.EmpReqNum, " +
                    "req.EmpReqStatus, pic.EmpReqPICId " +
                    "ORDER BY EmpReqExpDate",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMEmpRequest req " +
                    "INNER JOIN RCMBusinessUnit bu ON req.RcmBsUnitId = bu.RcmBsUnitId " +
                    "INNER JOIN RCMHRBP hrbp ON req.RcmHRBPId = hrbp.RcmHRBPId " +
                    "WHERE (:code IS NULL OR req.EmpReqCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR req.EmpReqName LIKE '%'+:name+'%') " +
                    "AND (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:hrbpName IS NULL OR hrbp.RcmHRBPName LIKE '%'+:hrbpName+'%')")
    Page<EmployeeRequestResp> getEmployeeRequest(@Param("code") String code,
                                                 @Param("name") String name,
                                                 @Param("buName") String buName,
                                                 @Param("hrbpName") String hrbpName,
                                                 @Param("username") String username,
                                                 Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT req.EmpReqCode AS code, req.EmpReqName AS name, " +
                    "CASE WHEN res.CanId IS NOT NULL THEN 'SUCCESS' " +
                    "WHEN act.EmpReqCanActId IS NOT NULL THEN 'FAILED' " +
                    "ELSE 'IN_PROGRESS' END AS status " +
                    "FROM RCMEmpReqCandidate can " +
                    "INNER JOIN RCMEmpRequest req ON can.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMEmpReqResult res ON res.CanId = can.CanId AND res.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMEmpReqCanActivity act ON can.EmpReqCanId = act.EmpReqCanId " +
                    "AND act.EmpReqCanActStatus = 'FAILED' " +
                    "WHERE can.CanId = :id")
    List<EmployeeRequestCandidateResp> getEmployeeRequestCandidate(@Param("id") Long id);
}
