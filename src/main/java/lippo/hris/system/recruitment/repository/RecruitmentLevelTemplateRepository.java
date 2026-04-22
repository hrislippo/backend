package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.RecruitmentLevelTemplate;
import lippo.hris.system.recruitment.response.RecruitmentTemplateResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecruitmentLevelTemplateRepository extends JpaRepository<RecruitmentLevelTemplate, Long> {

    @Query(nativeQuery = true,
            value = "SELECT t.* " +
                    "FROM RCMLvlTemplate t " +
                    "WHERE (:code IS NULL OR t.RCMLvlTempCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR t.RCMLvlTempName LIKE '%'+:name+'%')",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMLvlTemplate t " +
                    "WHERE (:code IS NULL OR t.RCMLvlTempCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR t.RCMLvlTempName LIKE '%'+:name+'%')")
    Page<RecruitmentLevelTemplate> getLevelTemplate(@Param("code") String code,
                                                    @Param("name") String name,
                                                    Pageable pageable);
}
