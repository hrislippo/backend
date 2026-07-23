package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "URMLoginAttempt", schema = "dbo")
public class LoginAttempt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LoginAttemptId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    private User user;

    @Column(name = "LoginAttemptFailedCount")
    private Integer failedCount;

    @Column(name = "LoginAttemptLockedUntil")
    private LocalDateTime lockedUntil;

    @Column(name = "LoginAttemptLastAttempt")
    private LocalDateTime lastAttempt;
}
