package lippo.hris.system.emailengine.repository;

import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.response.EmailTemplateResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    EmailTemplate findByName(String name);

    @Query(nativeQuery = true,
    value = "SELECT EmailTempId AS id, EmailTempCode AS code, EmailTempName AS name " +
            "FROM EmailTemplate")
    List<EmailTemplateResp> findAllList();

    @Query(nativeQuery = true,
    value = "SELECT EmailTempId AS id, EmailTempCode AS code, EmailTempName AS name " +
            "FROM EmailTemplate " +
            "WHERE (:code IS NULL OR EmailTempCode LIKE '%'+:code+'%') " +
            "AND (:name IS NULL OR EmailTempName LIKE '%'+:name+'%')",
    countQuery = "SELECT COUNT(1) " +
            "FROM EmailTemplate " +
            "WHERE (:code IS NULL OR EmailTempCode LIKE '%'+:code+'%') " +
            "AND (:name IS NULL OR EmailTempName LIKE '%'+:name+'%')")
    Page<EmailTemplateResp> getEmailTemplate(@Param("code") String code,
                                             @Param("name") String name,
                                             Pageable pageable);
}
