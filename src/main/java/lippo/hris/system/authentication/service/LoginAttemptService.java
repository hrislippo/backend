package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.LoginAttempt;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.LoginAttemptRepository;
import lippo.hris.system.exception.LockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class LoginAttemptService {

    @Autowired
    LoginAttemptRepository loginAttemptRepository;

    private static final int MAX_ATTEMPTS = 5;

    private static final Duration LOCK_DURATION = Duration.ofMinutes(10);

    public void checkLocked(User user) {
        LoginAttempt attempt = loginAttemptRepository.findByUser(user).orElse(null);

        if (attempt == null) return;
        if (attempt.getLockedUntil() == null) return;
        if (attempt.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new LockedException("Too many failed login attempts. Please try again in 10 minutes");
        }

        // Lock expired
        attempt.setFailedCount(0);
        attempt.setLockedUntil(null);
        loginAttemptRepository.save(attempt);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loginFailed(User user) {

        LoginAttempt attempt = loginAttemptRepository.findByUser(user).orElse(new LoginAttempt());
        attempt.setUser(user);

        Integer failed = attempt.getFailedCount() == null ? 0 : attempt.getFailedCount();
        failed++;
        attempt.setFailedCount(failed);
        attempt.setLastAttempt(LocalDateTime.now());

        if (failed >= MAX_ATTEMPTS) {
            attempt.setLockedUntil(LocalDateTime.now().plus(LOCK_DURATION));
        }

        loginAttemptRepository.save(attempt);
    }

    public void loginSucceeded(User user) {

        loginAttemptRepository.findByUser(user).ifPresent(attempt -> {
            attempt.setFailedCount(0);
            attempt.setLockedUntil(null);
            loginAttemptRepository.save(attempt);
        });
    }
}
