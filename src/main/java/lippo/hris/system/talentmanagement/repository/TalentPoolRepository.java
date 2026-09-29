package lippo.hris.system.talentmanagement.repository;

import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequest;
import lippo.hris.system.talentmanagement.response.TalentPoolResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TalentPoolRepository extends JpaRepository<TalentPool, Long> {
    TalentPool findByEmployeeNIK(String employeeNIK);

    @Query(nativeQuery = true,
            value="SELECT tl.TlTalentPoolEmpNIK AS employeeNIK, tl.TlTalentPoolEmpName AS employeeName, " +
                    "tpt.TlTalentPoolPotentName AS potential, tpf.TlTalentPoolPerfName AS performance " +
                    "FROM TLTalentPool tl " +
                    "LEFT JOIN TLTalentPoolPotent tpt ON tl.TlTalentPoolPotentId = tpt.TlTalentPoolPotentId " +
                    "LEFT JOIN TLTalentPoolPerf tpf ON tl.TlTalentPoolPerfId = tpf.TlTalentPoolPerfId " +
                    "WHERE (:empNIK IS NULL OR TlTalentPoolEmpNIK LIKE '%'+:empNIK+'%') AND " +
                    "(:empName IS NULL OR TlTalentPoolEmpName LIKE '%'+:empName+'%')",
    countQuery = "SELECT COUNT(1) FROM TLTalentPool " +
            "WHERE (:empNIK IS NULL OR TlTalentPoolEmpNIK LIKE '%'+:empNIK+'%') AND " +
            "(:empName IS NULL OR TlTalentPoolEmpName LIKE '%'+:empName+'%')")
    Page<TalentPoolResp> findAllByNIKAndName(@Param("empNIK") String empNIK, @Param("empName") String empName, Pageable pageable);
//    void deleteByPositionCodeAndEmployeeNIK(String positionCode, String employeeNIK);
//
//    @Query(nativeQuery = true,
//    value = "SELECT tp.TlTalentPoolPosCode AS positionCode, tp.TlTalentPoolPosName AS positionName, " +
//            "COUNT(1) AS successorCount " +
//            "FROM TLTalentPool tp " +
//            "WHERE (:posCode IS NULL OR tp.TlTalentPoolPosCode LIKE '%'+:posCode+'%') " +
//            "AND (:posName IS NULL OR tp.TlTalentPoolPosName LIKE '%'+:posName+'%') " +
//            "GROUP BY tp.TlTalentPoolPosCode, tp.TlTalentPoolPosName",
//    countQuery = "SELECT COUNT(DISTINCT tp.TlTalentPoolPosCode) FROM TLTalentPool tp " +
//            "WHERE (:posCode IS NULL OR tp.TlTalentPoolPosCode LIKE '%'+:posCode+'%') " +
//            "AND (:posName IS NULL OR tp.TlTalentPoolPosName LIKE '%'+:posName+'%')")
//    Page<TalentPoolResp> findAllByPosition(@Param("posCode") String posCode,
//                                           @Param("posName") String posName,
//                                           Pageable pageable);
}
