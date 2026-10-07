package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserTemplateAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTemplateAuthRepository extends JpaRepository<UserTemplateAuth, Long> {
    List<UserTemplateAuth> findByTemplateAuth(TemplateAuth templateAuth);
    List<UserTemplateAuth> findByUser(User user);
    UserTemplateAuth findByUserAndTemplateAuth(User user, TemplateAuth templateAuth);
}
