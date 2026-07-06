package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT m.* " +
                    "FROM URMMenu m " +
                    "JOIN URMMenuRole mr ON m.MenuId = mr.MenuId " +
                    "JOIN URMRole r ON mr.RoleId = r.RoleId " +
                    "WHERE r.RoleName IN (:roles) " +
                    "ORDER BY m.MenuOrder")
    List<Menu> findMenusByRoles(@Param("roles") List<String> roles);
}
