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
            value="SELECT DISTINCT tad.TempAuthDtOrgCode FROM URMTempAuth ta " +
                    "INNER JOIN URMTempAuthDt tad ON ta.TempAuthId = tad.TempAuthId " +
                    "WHERE ta.TempAuthName IN (:tempAuthName)")
    List<String> findOrgCodeByTempName(@Param("tempAuthName") List<String> tempAuthName);
}
