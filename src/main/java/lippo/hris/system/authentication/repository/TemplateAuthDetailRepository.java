package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.TemplateAuthDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateAuthDetailRepository extends JpaRepository<TemplateAuthDetail, Long> {
    List<TemplateAuthDetail> findByTemplateAuth(TemplateAuth templateAuth);
    TemplateAuthDetail findByTemplateAuthAndOrganizationCode(TemplateAuth templateAuth, String organizationCode);
}
