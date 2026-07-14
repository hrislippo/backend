package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.response.UserResponse;
import lippo.hris.system.authentication.response.UserResponsev2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByusername(String username);
    List<UserResponsev2> findByActiveTrue();
    UserResponsev2 findByUsername(String username);

    @Query(nativeQuery = true,
            value="SELECT u.UserName, u.UserRealName AS name, STRING_AGG(r.RoleName, ', ') AS roles, u.UserActive " +
                    "FROM URMUser u " +
                    "LEFT JOIN URMUserRole ur ON u.UserId = ur.UserId " +
                    "LEFT JOIN URMRole r ON ur.RoleId = r.RoleId " +
                    "WHERE (:usernameSearch IS NULL OR u.UserName LIKE CONCAT('%',:usernameSearch,'%')) " +
                    "AND (:nameSearch IS NULL OR u.UserRealName LIKE '%'+:nameSearch+'%') " +
                    "AND (:roleSearch IS NULL OR r.RoleName LIKE CONCAT('%',:roleSearch,'%')) " +
                    "AND (:activeSearch IS NULL OR u.UserActive = :activeSearch) " +
                    "GROUP BY u.UserName, u.UserActive, u.UserRealName",
            countQuery = "SELECT COUNT(1) " +
                    "FROM URMUser u " +
                    "LEFT JOIN URMUserRole ur ON u.UserId = ur.UserId " +
                    "LEFT JOIN URMRole r ON ur.RoleId = r.RoleId " +
                    "WHERE (:usernameSearch IS NULL OR u.UserName LIKE CONCAT('%',:usernameSearch,'%')) " +
                    "AND (:nameSearch IS NULL OR u.UserRealName LIKE '%'+:nameSearch+'%') " +
                    "AND (:roleSearch IS NULL OR r.RoleName LIKE CONCAT('%',:roleSearch,'%')) " +
                    "AND (:activeSearch IS NULL OR u.UserActive = :activeSearch) " +
                    "GROUP BY u.UserName, u.UserActive, u.UserRealName")
    Page<UserResponse> findAllWithRolesAndSearch(@Param("usernameSearch") String usernameSearch,
                                                 @Param("nameSearch") String nameSearch,
                                                 @Param("roleSearch") String roleSearch,
                                                 @Param("activeSearch") Boolean activeSearch,
                                                 Pageable pageable);

    @Query(nativeQuery = true,
    value = "SELECT u.UserId AS id, u.UserRealName AS name, " +
            "u.UserName AS username, u.UserActive AS active " +
            "FROM URMUser u " +
            "INNER JOIN URMUserRole ur ON u.UserId = ur.UserId " +
            "INNER JOIN URMRole r ON ur.RoleId = r.RoleId " +
            "WHERE r.RoleName = :role " +
            "ORDER BY u.UserRealName")
    List<UserResponsev2> findByActiveTrueAndRole(@Param("role") String role);
}
