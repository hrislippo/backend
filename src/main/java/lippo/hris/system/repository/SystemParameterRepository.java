package lippo.hris.system.repository;

import lippo.hris.system.entity.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {

    SystemParameter findByKey(String key);
}
