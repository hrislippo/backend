package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.TemplateAuthDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateAuthDetailRepository extends JpaRepository<TemplateAuthDetail, Long> {
    List<TemplateAuthDetail> findByTemplateAuth(TemplateAuth templateAuth);

    @Query(nativeQuery = true, value = "SELECT * FROM URMTempAuthDt WHERE TempAuthId = :tempAuthId " +
            "AND (:orgCode IS NULL OR TempAuthDtOrgCode = :orgCode) AND (:jobLvlCode IS NULL OR TempAuthDtJobLvlCode = :jobLvlCode) " +
            "AND (:compCode IS NULL OR TempAuthDtCompCode = :compCode) AND (:locCode IS NULL OR TempAuthDtLocCode = :locCode) " +
            "AND (:posCode IS NULL OR TempAuthDtPosCode = :posCode) AND (:jobTtlCode IS NULL OR TempAuthDtJobTtlCode = :jobTtlCode)")
    TemplateAuthDetail findByTemplateAuthAndCriteria(@Param("tempAuthId") Long tempAuthId,
                                                     @Param("orgCode") String orgCode,
                                                     @Param("jobLvlCode") String jobLvlCode,
                                                     @Param("compCode") String compCode,
                                                     @Param("locCode") String locCode,
                                                     @Param("posCode") String posCode,
                                                     @Param("jobTtlCode") String jobTtlCode);
}
