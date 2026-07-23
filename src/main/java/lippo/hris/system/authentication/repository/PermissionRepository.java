package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
