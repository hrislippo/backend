package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.TemplateAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateAuthRepository extends JpaRepository<TemplateAuth, Long> {
    @Query(nativeQuery = true,
            value="SELECT * FROM URMTempAuth " +
                    "WHERE (:tempCode IS NULL OR TempAuthCode LIKE '%'+:tempCode+'%') " +
                    "AND (:tempName IS NULL OR TempAuthName LIKE '%'+:tempName+'%')",
            countQuery = "SELECT COUNT(1) FROM URMTempAuth " +
                    "WHERE (:tempCode IS NULL OR TempAuthCode LIKE '%'+:tempCode+'%') " +
                    "AND (:tempName IS NULL OR TempAuthName LIKE '%'+:tempName+'%')")
    Page<TemplateAuth> findAllByCodeAndName(@Param("tempCode") String tempCode, @Param("tempName") String tempName, Pageable pageable);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tad.TempAuthDtCompCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName) AND tad.TempAuthDtCompCode IS NOT NULL")
    List<String> findCompCodeByTempName(@Param("tempAuthName") List<String> tempAuthName);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tad.TempAuthDtOrgCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName) AND tad.TempAuthDtOrgCode IS NOT NULL")
    List<String> findOrgCodeByTempName(@Param("tempAuthName") List<String> tempAuthName);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tad.TempAuthDtJobLvlCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName) AND tad.TempAuthDtJobLvlCode IS NOT NULL")
    List<String> findJobLevelByTempName(@Param("tempAuthName") List<String> tempAuthName);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tad.TempAuthDtJobTtlCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName) AND tad.TempAuthDtJobTtlCode IS NOT NULL")
    List<String> findJobTitleByTempName(@Param("tempAuthName") List<String> tempAuthName);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tad.TempAuthDtPosCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName) AND tad.TempAuthDtPosCode IS NOT NULL")
    List<String> findPosCodeByTempName(@Param("tempAuthName") List<String> tempAuthName);

    @Query(nativeQuery = true,
            value="SELECT DISTINCT tad.TempAuthDtLocCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName) AND tad.TempAuthDtLocCode IS NOT NULL")
    List<String> findLocCodeByTempName(@Param("tempAuthName") List<String> tempAuthName);
}
