package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.response.EmployeeRequestCandidateResp;
import lippo.hris.system.recruitment.response.EmployeeRequestResp;
import lippo.hris.system.recruitment.response.EmployeeRequestSLAResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRequestFormRepository extends JpaRepository<EmployeeRequestForm, Long> {
    @Query(nativeQuery = true,
    value = "SELECT COUNT(1) " +
            "FROM RCMEmpRequest req " +
            "INNER JOIN RCMHRBP h ON req.RcmHRBPId = h.RcmHRBPId " +
            "WHERE req.EmpReqStatus IN ('OPEN', 'IN_PROGRESS') " +
            "AND (:flagHRBP = CAST(0 AS BIT) OR h.RcmHRBPId IN " +
            "(SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :username)))")
    Integer countOpenRequest(@Param("username") String username,
                             @Param("flagHRBP") Boolean flagHRBP);

    @Query(nativeQuery = true,
    value = "SELECT TOP 1 REPLACE(EmpReqCode, :prefix, '') FROM RCMEmpRequest ORDER BY EmpReqCode DESC")
    Long countByEmpReqCodeStartingWith(@Param("prefix") String prefix);

    @Query(nativeQuery = true,
            value = "WITH StageOrder AS (SELECT * FROM (VALUES " +
                    "(1, 'Assessment'), (2, 'Offering'), (3, 'Background Check'), (4, 'Sign Agreement'), (5, 'Onboarding')) v(StageOrder, StageName)), " +
                    "StageAgg AS (SELECT req.EmpReqId, so.StageOrder, so.StageName, " +
                    "ROW_NUMBER() OVER (PARTITION BY req.EmpReqId ORDER BY so.StageOrder DESC) AS rn " +
                    "FROM RCMEmpReqCanActivity ca " +
                    "JOIN RCMEmpReqCandidate rc ON rc.EmpReqCanId = ca.EmpReqCanId " +
                    "JOIN RCMEmpRequest req ON req.EmpReqId = rc.EmpReqId " +
                    "JOIN RCMACTActivity act ON act.RcmActId = ca.RcmActId " +
                    "JOIN StageOrder so ON so.StageName = act.RcmActGrp " +
                    "WHERE req.EmpReqStatus = 'IN_PROGRESS' AND ca.EmpReqCanActStatus IN ('IN_PROGRESS', 'COMPLETED') " +
                    "GROUP BY req.EmpReqId, so.StageOrder, so.StageName), " +
                    "OfferingCompleted AS(SELECT rc.EmpReqId, COUNT(DISTINCT rc.EmpReqCanId) AS CompletedOfferingCandidate " +
                    "FROM RCMEmpReqCandidate rc " +
                    "JOIN RCMEmpReqCanActivity ca ON rc.EmpReqCanId = ca.EmpReqCanId " +
                    "JOIN RCMACTActivity act ON act.RcmActId = ca.RcmActId " +
                    "WHERE act.RcmActGrp = 'Offering' AND ca.EmpReqCanActStatus = 'COMPLETED' " +
                    "AND NOT EXISTS (SELECT 1 FROM RCMEmpReqCanActivity ca2 " +
                    "JOIN RCMACTActivity act2 ON act2.RcmActId = ca2.RcmActId " +
                    "WHERE ca2.EmpReqCanId = rc.EmpReqCanId " +
                    "AND act2.RcmActGrp IN ('Background Check', 'Sign Agreement', 'Onboarding') " +
                    "AND ca2.EmpReqCanActStatus = 'FAILED') GROUP BY rc.EmpReqId) " +
                    "SELECT * FROM ( " +
                    "SELECT COUNT(res.EmpReqResId) AS recruitNumber, " +
                    "req.EmpReqId AS id, req.EmpReqCode AS code, req.EmpReqName AS name, " +
                    "bu.RcmBsUnitName AS businessUnitName, hrbp.RcmHRBPName AS hrbpName, " +
                    "req.EmpReqExpDate AS expDate, req.EmpReqNum AS requestNumber, " +
                    "req.EmpReqStatus AS status, req.EmpReqStartDate AS startDate, " +
                    "STRING_AGG(usr.UserRealName, ', ') AS pic, " +
                    "CASE WHEN req.EmpReqStatus = 'IN_PROGRESS' AND agg.StageName IS NULL THEN 'Sourcing' ELSE agg.StageName END AS stage, " +
                    "CASE WHEN pic.EmpReqPICId IS NOT NULL THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS eligible, " +
                    "CASE WHEN MAX(ISNULL(oc.CompletedOfferingCandidate, 0)) >= req.EmpReqNum THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS StopSLA, " +
                    "CASE WHEN MAX(ISNULL(oc.CompletedOfferingCandidate, 0)) >= req.EmpReqNum OR req.EmpReqStatus IN ('ON_HOLD', 'CANCELLED', 'COMPLETED') THEN 'No SLA' " +
                    "WHEN ((DATEDIFF(SECOND, req.EmpReqStartDate, GETDATE()) * 100.0) / NULLIF(DATEDIFF(SECOND, req.EmpReqStartDate, req.EmpReqExpDate), 0)) >= 100 THEN 'Expired' " +
                    "WHEN ((DATEDIFF(SECOND, req.EmpReqStartDate, GETDATE()) * 100.0) / NULLIF(DATEDIFF(SECOND, req.EmpReqStartDate, req.EmpReqExpDate), 0)) >= 81 THEN 'Warning' " +
                    "WHEN ((DATEDIFF(SECOND, req.EmpReqStartDate, GETDATE()) * 100.0) / NULLIF(DATEDIFF(SECOND, req.EmpReqStartDate, req.EmpReqExpDate), 0)) >= 61 THEN 'Moderate' " +
                    "ELSE 'Safe' END AS deadlineStatus " +
                    "FROM RCMEmpRequest req " +
                    "LEFT JOIN OfferingCompleted oc ON oc.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMEmpReqResult res ON req.EmpReqId = res.EmpReqId " +
                    "LEFT JOIN RCMEmpReqPIC pic ON pic.EmpReqId = req.EmpReqId " +
                    "AND pic.UserId = (SELECT UserId FROM URMUser WHERE UserName = :userName) " +
                    "INNER JOIN RCMBusinessUnit bu ON req.RcmBsUnitId = bu.RcmBsUnitId " +
                    "INNER JOIN RCMHRBP hrbp ON req.RcmHRBPId = hrbp.RcmHRBPId " +
                    "LEFT JOIN RCMEmpReqPIC pic2 ON pic2.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN URMUser usr ON pic2.UserId = usr.UserId " +
                    "LEFT JOIN (SELECT EmpReqId, StageOrder, StageName FROM StageAgg WHERE rn = 1) agg " +
                    "ON agg.EmpReqId = req.EmpReqId " +
                    "WHERE (:flagHRBP = CAST(0 AS BIT) OR hrbp.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :userName))) " +
                    "AND (:code IS NULL OR req.EmpReqCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR req.EmpReqName LIKE '%'+:name+'%') " +
                    "AND (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:hrbpName IS NULL OR hrbp.RcmHRBPName LIKE '%'+:hrbpName+'%') " +
                    "AND (:pic IS NULL OR EXISTS (SELECT 1 FROM RCMEmpReqPIC p JOIN URMUser u ON u.UserId = p.UserId " +
                    "WHERE p.EmpReqId = req.EmpReqId AND u.UserRealName LIKE '%' + :pic + '%')) " +
                    "GROUP BY req.EmpReqId, req.EmpReqCode, req.EmpReqName, " +
                    "bu.RcmBsUnitName, hrbp.RcmHRBPName, req.EmpReqExpDate, req.EmpReqStartDate, req.EmpReqNum, " +
                    "req.EmpReqStatus, pic.EmpReqPICId, agg.StageName) a " +
                    "WHERE (:sla IS NULL OR a.deadlineStatus = :sla) " +
                    "AND (:status IS NULL OR COALESCE(a.stage, a.status) = :status) " +
                    "ORDER BY CASE WHEN a.status = 'IN_PROGRESS' THEN 0 WHEN a.status = 'OPEN' THEN 1 ELSE 2 END, a.businessUnitName, a.expDate",
            countQuery = "WITH StageOrder AS (SELECT * FROM (VALUES " +
                    "(1, 'Assessment'), (2, 'Offering'), (3, 'Background Check'), (4, 'Sign Agreement'), (5, 'Onboarding')) v(StageOrder, StageName)), " +
                    "StageAgg AS (SELECT req.EmpReqId, so.StageOrder, so.StageName, " +
                    "ROW_NUMBER() OVER (PARTITION BY req.EmpReqId ORDER BY so.StageOrder DESC) AS rn " +
                    "FROM RCMEmpReqCanActivity ca " +
                    "JOIN RCMEmpReqCandidate rc ON rc.EmpReqCanId = ca.EmpReqCanId " +
                    "JOIN RCMEmpRequest req ON req.EmpReqId = rc.EmpReqId " +
                    "JOIN RCMACTActivity act ON act.RcmActId = ca.RcmActId " +
                    "JOIN StageOrder so ON so.StageName = act.RcmActGrp " +
                    "WHERE req.EmpReqStatus = 'IN_PROGRESS' AND ca.EmpReqCanActStatus IN ('IN_PROGRESS', 'COMPLETED') " +
                    "GROUP BY req.EmpReqId, so.StageOrder, so.StageName), " +
                    "OfferingCompleted AS(SELECT rc.EmpReqId, COUNT(DISTINCT rc.EmpReqCanId) AS CompletedOfferingCandidate " +
                    "FROM RCMEmpReqCandidate rc " +
                    "JOIN RCMEmpReqCanActivity ca ON rc.EmpReqCanId = ca.EmpReqCanId " +
                    "JOIN RCMACTActivity act ON act.RcmActId = ca.RcmActId " +
                    "WHERE act.RcmActGrp = 'Offering' AND ca.EmpReqCanActStatus = 'COMPLETED' " +
                    "AND NOT EXISTS (SELECT 1 FROM RCMEmpReqCanActivity ca2 " +
                    "JOIN RCMACTActivity act2 ON act2.RcmActId = ca2.RcmActId " +
                    "WHERE ca2.EmpReqCanId = rc.EmpReqCanId " +
                    "AND act2.RcmActGrp IN ('Background Check', 'Sign Agreement', 'Onboarding') " +
                    "AND ca2.EmpReqCanActStatus = 'FAILED') GROUP BY rc.EmpReqId) " +
                    "SELECT COUNT(1) FROM ( " +
                    "SELECT COUNT(res.EmpReqResId) AS recruitNumber, " +
                    "req.EmpReqId AS id, req.EmpReqCode AS code, req.EmpReqName AS name, " +
                    "bu.RcmBsUnitName AS businessUnitName, hrbp.RcmHRBPName AS hrbpName, " +
                    "req.EmpReqExpDate AS expDate, req.EmpReqNum AS requestNumber, " +
                    "req.EmpReqStatus AS status, req.EmpReqStartDate AS startDate, " +
                    "STRING_AGG(usr.UserRealName, ', ') AS pic, " +
                    "CASE WHEN req.EmpReqStatus = 'IN_PROGRESS' AND agg.StageName IS NULL THEN 'Sourcing' ELSE agg.StageName END AS stage, " +
                    "CASE WHEN pic.EmpReqPICId IS NOT NULL THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS eligible, " +
                    "CASE WHEN MAX(ISNULL(oc.CompletedOfferingCandidate, 0)) >= req.EmpReqNum THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS StopSLA, " +
                    "CASE WHEN MAX(ISNULL(oc.CompletedOfferingCandidate, 0)) >= req.EmpReqNum OR req.EmpReqStatus IN ('ON_HOLD', 'CANCELLED', 'COMPLETED') THEN 'No SLA' " +
                    "WHEN ((DATEDIFF(SECOND, req.EmpReqStartDate, GETDATE()) * 100.0) / NULLIF(DATEDIFF(SECOND, req.EmpReqStartDate, req.EmpReqExpDate), 0)) >= 100 THEN 'Expired' " +
                    "WHEN ((DATEDIFF(SECOND, req.EmpReqStartDate, GETDATE()) * 100.0) / NULLIF(DATEDIFF(SECOND, req.EmpReqStartDate, req.EmpReqExpDate), 0)) >= 81 THEN 'Warning' " +
                    "WHEN ((DATEDIFF(SECOND, req.EmpReqStartDate, GETDATE()) * 100.0) / NULLIF(DATEDIFF(SECOND, req.EmpReqStartDate, req.EmpReqExpDate), 0)) >= 61 THEN 'Moderate' " +
                    "ELSE 'Safe' END AS deadlineStatus " +
                    "FROM RCMEmpRequest req " +
                    "LEFT JOIN OfferingCompleted oc ON oc.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN RCMEmpReqResult res ON req.EmpReqId = res.EmpReqId " +
                    "LEFT JOIN RCMEmpReqPIC pic ON pic.EmpReqId = req.EmpReqId " +
                    "AND pic.UserId = (SELECT UserId FROM URMUser WHERE UserName = :userName) " +
                    "INNER JOIN RCMBusinessUnit bu ON req.RcmBsUnitId = bu.RcmBsUnitId " +
                    "INNER JOIN RCMHRBP hrbp ON req.RcmHRBPId = hrbp.RcmHRBPId " +
                    "LEFT JOIN RCMEmpReqPIC pic2 ON pic2.EmpReqId = req.EmpReqId " +
                    "LEFT JOIN URMUser usr ON pic2.UserId = usr.UserId " +
                    "LEFT JOIN (SELECT EmpReqId, StageOrder, StageName FROM StageAgg WHERE rn = 1) agg " +
                    "ON agg.EmpReqId = req.EmpReqId " +
                    "WHERE (:flagHRBP = CAST(0 AS BIT) OR hrbp.RcmHRBPId IN (SELECT uh.RcmHRBPId FROM URMUserHRBP uh WHERE uh.UserId = (SELECT UserId FROM URMUser WHERE UserName = :userName))) " +
                    "AND (:code IS NULL OR req.EmpReqCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR req.EmpReqName LIKE '%'+:name+'%') " +
                    "AND (:buName IS NULL OR bu.RcmBsUnitName LIKE '%'+:buName+'%') " +
                    "AND (:hrbpName IS NULL OR hrbp.RcmHRBPName LIKE '%'+:hrbpName+'%') " +
                    "AND (:pic IS NULL OR EXISTS (SELECT 1 FROM RCMEmpReqPIC p JOIN URMUser u ON u.UserId = p.UserId " +
                    "WHERE p.EmpReqId = req.EmpReqId AND u.UserRealName LIKE '%' + :pic + '%')) " +
                    "GROUP BY req.EmpReqId, req.EmpReqCode, req.EmpReqName, " +
                    "bu.RcmBsUnitName, hrbp.RcmHRBPName, req.EmpReqExpDate, req.EmpReqStartDate, req.EmpReqNum, " +
                    "req.EmpReqStatus, pic.EmpReqPICId, agg.StageName) a " +
                    "WHERE (:sla IS NULL OR a.deadlineStatus = :sla) " +
                    "AND (:status IS NULL OR COALESCE(a.stage, a.status) = :status) ")
    Page<EmployeeRequestResp> getEmployeeRequest(@Param("code") String code,
                                                 @Param("name") String name,
                                                 @Param("buName") String buName,
                                                 @Param("hrbpName") String hrbpName,
                                                 @Param("userName") String username,
                                                 @Param("pic") String pic,
                                                 @Param("sla") String sla,
                                                 @Param("status") String status,
                                                 @Param("flagHRBP") Boolean flagHRBP,
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

    @Query(nativeQuery = true,
            value = "EXECUTE sp_GetTop10EmployeeRequestDashboard @FlagHRBP = :flagHRBP, @Username = :username")
    List<EmployeeRequestSLAResp> getEmployeeRequestSLA(@Param("username") String username,
                                                       @Param("flagHRBP") Boolean flagHRBP);
}
