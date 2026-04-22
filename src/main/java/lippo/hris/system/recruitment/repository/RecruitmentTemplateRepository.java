package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.RecruitmentTemplate;
import lippo.hris.system.recruitment.response.RecruitmentTemplateResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentTemplateRepository extends JpaRepository<RecruitmentTemplate, Long> {

    @Query(nativeQuery = true,
            value = "SELECT t.RcmTempId AS id, t.RCMTempCode AS code, t.RCMTempName AS name " +
                    "FROM RCMACTTemplate t " +
                    "WHERE (:code IS NULL OR t.RCMTempCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR t.RCMTempName LIKE '%'+:name+'%')",
            countQuery = "SELECT COUNT(1) " +
                    "FROM RCMACTTemplate t " +
                    "WHERE (:code IS NULL OR t.RCMTempCode LIKE '%'+:code+'%') " +
                    "AND (:name IS NULL OR t.RCMTempName LIKE '%'+:name+'%')")
    Page<RecruitmentTemplateResp> getTemplate(@Param("code") String code,
                                              @Param("name") String name,
                                              Pageable pageable);
}
