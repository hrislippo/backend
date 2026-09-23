package lippo.hris.system.talentmanagement.repository;

import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.response.TalentPoolResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TalentPoolRepository extends JpaRepository<TalentPool, Long> {
    List<TalentPool> findByPositionCode(String positionCode);
    void deleteByPositionCodeAndEmployeeNIK(String positionCode, String employeeNIK);

    @Query(nativeQuery = true,
    value = "SELECT tp.TlTalentPoolPosCode AS positionCode, tp.TlTalentPoolPosName AS positionName, " +
            "COUNT(1) AS successorCount " +
            "FROM TLTalentPool tp " +
            "WHERE (:posCode IS NULL OR tp.TlTalentPoolPosCode LIKE '%'+:posCode+'%') " +
            "AND (:posName IS NULL OR tp.TlTalentPoolPosName LIKE '%'+:posName+'%') " +
            "GROUP BY tp.TlTalentPoolPosCode, tp.TlTalentPoolPosName",
    countQuery = "SELECT COUNT(DISTINCT tp.TlTalentPoolPosCode) FROM TLTalentPool tp " +
            "WHERE (:posCode IS NULL OR tp.TlTalentPoolPosCode LIKE '%'+:posCode+'%') " +
            "AND (:posName IS NULL OR tp.TlTalentPoolPosName LIKE '%'+:posName+'%')")
    Page<TalentPoolResp> findAllByPosition(@Param("posCode") String posCode,
                                           @Param("posName") String posName,
                                           Pageable pageable);
}
