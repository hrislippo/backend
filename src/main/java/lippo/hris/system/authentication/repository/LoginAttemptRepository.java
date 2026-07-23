package lippo.hris.system.authentication.repository;

import lippo.hris.system.authentication.entity.LoginAttempt;
import lippo.hris.system.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAttemptRepository  extends JpaRepository<LoginAttempt, Long> {
    Optional<LoginAttempt> findByUser(User user);
}
