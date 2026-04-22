package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RCMCANCanData", schema = "dbo")
public class Candidate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanId")
    private Long id;

    @Column(name = "CanNum")
    private String canNumber;

    @Column(name = "CanName", nullable = false)
    private String name;

    @Column(name = "CanBirthDate")
    private LocalDate birthDate;

    @Column(name = "CanHashtag")
    private String hashtag;

    @Column(name = "CanCurrSal")
    private Long currentSalary;

    @Column(name = "CanExpSal")
    private Long expectedSalary;

    @Column(name = "CanFgShortlist")
    private Boolean flagShortlist = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    private User user;
}
