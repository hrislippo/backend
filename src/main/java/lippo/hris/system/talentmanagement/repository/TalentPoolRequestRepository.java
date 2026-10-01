package lippo.hris.system.talentmanagement.repository;

import lippo.hris.system.talentmanagement.entity.TalentPoolRequest;
import lippo.hris.system.talentmanagement.response.TalentPoolEmployeeReadinessResp;
import lippo.hris.system.talentmanagement.response.TalentPoolRequestNineBoxResp;
import lippo.hris.system.talentmanagement.response.TalentPoolRequestResp;
import lippo.hris.system.talentmanagement.response.TalentPoolStructureResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TalentPoolRequestRepository extends JpaRepository<TalentPoolRequest, Long> {
    TalentPoolRequest findByPositionCode (String positionCode);

    @Query(nativeQuery = true,
            value="SELECT hd.TlTalentPoolReqHdPosCode AS positionCode, hd.TlTalentPoolReqHdPosName AS positionName, hd.TlTalentPoolReqHdPilarName AS pilarName, " +
                    "hd.TlTalentPoolReqHdBuName AS buName, COUNT(dt.TlTalentPoolReqDtId) AS successorCount " +
                    "FROM TLTalentPoolReqHd hd " +
                    "LEFT JOIN TLTalentPoolReqDt dt ON hd.TlTalentPoolReqHdId = dt.TlTalentPoolReqHdId " +
                    "WHERE (:posName IS NULL OR hd.TlTalentPoolReqHdPosName LIKE '%'+:posName+'%') " +
                    "AND (:pilarName IS NULL OR hd.TlTalentPoolReqHdPilarName LIKE '%'+:pilarName+'%') " +
                    "AND (:buName IS NULL OR hd.TlTalentPoolReqHdBuName LIKE '%'+:buName+'%') " +
                    "GROUP BY hd.TlTalentPoolReqHdPosCode, hd.TlTalentPoolReqHdPosName, hd.TlTalentPoolReqHdPilarName, hd.TlTalentPoolReqHdBuName",
            countQuery="SELECT COUNT(1) FROM TLTalentPoolReqHd hd " +
                    "WHERE (:posName IS NULL OR hd.TlTalentPoolReqHdPosName LIKE '%'+:posName+'%') " +
                    "AND (:pilarName IS NULL OR hd.TlTalentPoolReqHdPilarName LIKE '%'+:pilarName+'%') " +
                    "AND (:buName IS NULL OR hd.TlTalentPoolReqHdBuName LIKE '%'+:buName+'%')")
    Page<TalentPoolRequestResp> findTalentPoolRequest(@Param("posName") String posName, @Param("pilarName") String pilarName, @Param("buName") String buName, Pageable pageable);

    @Query(nativeQuery = true,
    value="SELECT DISTINCT tp.TlTalentPoolId AS id, tp.TlTalentPoolEmpNIK AS employeeNIK, tp.TlTalentPoolEmpName AS employeeName, " +
            "tp.TlTalentPoolPotentId AS potential, tp.TlTalentPoolPerfId AS performance " +
            "FROM TLTalentPool tp " +
            "LEFT JOIN TLTalentPoolReqDt dt ON tp.TlTalentPoolId = dt.TlTalentPoolReqDtTalent " +
            "LEFT JOIN TLTalentPoolReqHd hd ON dt.TlTalentPoolReqHdId = hd.TlTalentPoolReqHdId " +
            "WHERE (:posCode IS NULL OR hd.TlTalentPoolReqHdPosCode = :posCode)")
    List<TalentPoolRequestNineBoxResp> findTalentPoolRequestNineBox(@Param("posCode") String posCode);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tp.TlTalentPoolEmpNIK AS employeeNIK, tp.TlTalentPoolEmpName AS employeeName, " +
                    "ms.TlTalentPoolReadinessName AS readiness " +
                    "FROM TLTalentPoolReqHd hd " +
                    "LEFT JOIN TLTalentPoolReqDt dt ON hd.TlTalentPoolReqHdId = dt.TlTalentPoolReqHdId " +
                    "LEFT JOIN TLTalentPool tp ON dt.TlTalentPoolReqDtTalent = tp.TlTalentPoolId " +
                    "LEFT JOIN TLTalentPoolReadinessMs ms ON dt.TlTalentPoolReqDtReadiness = ms.TlTalentPoolReadinessMsId " +
                    "WHERE (:posCode IS NULL OR hd.TlTalentPoolReqHdPosCode = :posCode)")
    List<TalentPoolEmployeeReadinessResp> findTalentPoolRequestReadiness(@Param("posCode") String posCode);

    @Query(nativeQuery = true,
            value="SELECT tp.TlTalentPoolEmpNIK AS empNik, tp.TlTalentPoolEmpName AS empName, hd.TlTalentPoolReqHdPosName AS posName " +
                    "FROM TLTalentPoolReqHd hd " +
                    "INNER JOIN TLTalentPool tp ON hd.TlTalentPoolReqHdTalent = tp.TlTalentPoolId " +
                    "WHERE (:empName IS NULL OR tp.TlTalentPoolEmpName LIKE '%'+:empName+'%') " +
                    "AND (:posName IS NULL OR hd.TlTalentPoolReqHdPosName LIKE '%'+:posName+'%')",
            countQuery="SELECT COUNT(1) FROM TLTalentPoolReqHd hd " +
                    "INNER JOIN TLTalentPool tp ON hd.TlTalentPoolReqHdTalent = tp.TlTalentPoolId " +
                    "WHERE (:empName IS NULL OR tp.TlTalentPoolEmpName LIKE '%'+:empName+'%') " +
                    "AND (:posName IS NULL OR hd.TlTalentPoolReqHdPosName LIKE '%'+:posName+'%')")
    Page<TalentPoolStructureResp> findTalentPoolRequest(@Param("empName") String empName, @Param("posName") String posName, Pageable pageable);
}
