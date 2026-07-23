package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.PermissionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRoleRepository extends JpaRepository<PermissionRole, Long> {

    @Query(nativeQuery = true,
            value="SELECT DISTINCT p.PermissionCode " +
                    "FROM URMPermission p " +
                    "LEFT JOIN URMPermissionRole pr ON p.PermissionId = pr.PermissionId " +
                    "LEFT JOIN URMUserRole ur ON pr.RoleId = ur.RoleId " +
                    "LEFT JOIN URMUser u ON ur.UserId = u.UserId " +
                    "WHERE u.UserName = :userNameParam")
    List<String> findByUser(@Param("userNameParam")String userNameParam);
}
