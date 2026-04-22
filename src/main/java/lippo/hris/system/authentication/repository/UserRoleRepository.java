package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.Role;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByUserAndRole(User user, Role role);

    @Query(nativeQuery = true,
            value="SELECT r.RoleName " +
                    "FROM URMUser u " +
                    "LEFT JOIN URMUserRole ur ON u.UserId = ur.UserId " +
                    "LEFT JOIN URMRole r ON ur.RoleId = r.RoleId " +
                    "WHERE u.UserName = :userNameParam")
    List<String> findByUser(@Param("userNameParam")String userNameParam);

    void deleteByUser(User user);
}