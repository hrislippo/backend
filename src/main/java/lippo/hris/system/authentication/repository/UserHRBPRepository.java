package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserHRBP;
import lippo.hris.system.organization.entity.HRBP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserHRBPRepository extends JpaRepository<UserHRBP, Long> {

    UserHRBP findByUserAndHrbp(User user, HRBP hrbp);
    List<UserHRBP> findByUser(User user);

    @Query(nativeQuery = true,
            value="SELECT h.RcmHRBPCode " +
                    "FROM URMUser u " +
                    "LEFT JOIN URMUserHRBP uh ON u.UserId = uh.UserId " +
                    "LEFT JOIN RCMHRBP h ON uh.RcmHRBPId = h.RcmHRBPId " +
                    "WHERE u.UserName = :userNameParam")
    List<String> findByUser(@Param("userNameParam")String userNameParam);
}
