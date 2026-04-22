package lippo.hris.system.recruitment.repository;

import lippo.hris.system.recruitment.entity.RecruitmentActivityTemplate;
import lippo.hris.system.recruitment.entity.RecruitmentTemplate;
import lippo.hris.system.recruitment.response.RecruitmentActivityTemplateResp;
import lippo.hris.system.recruitment.response.RecruitmentTemplateResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentActivityTemplateRepository extends JpaRepository<RecruitmentActivityTemplate, Long> {

    @Query(nativeQuery = true,
            value = "SELECT a.RcmActId AS id, a.RcmActName AS name, a.RcmActGrp AS groupName " +
                    "FROM RCMACTActivityTemplate t " +
                    "INNER JOIN RCMACTActivity a ON t.RcmActId = a.RcmActId " +
                    "WHERE t.RcmTempId = :tempId " +
                    "ORDER BY t.RcmActOrder")
    List<RecruitmentActivityTemplateResp> getActivitiesByTemplate(@Param("tempId") Long tempId);

    List<RecruitmentActivityTemplate> findByTemplate(RecruitmentTemplate template);

    void deleteAllByTemplate(RecruitmentTemplate template);
}
