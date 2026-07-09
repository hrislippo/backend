package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lippo.hris.system.recruitment.entity.HRBP;
import lombok.Data;

@Data
@Entity
@Table(name = "URMUserHRBP", schema = "dbo")
public class UserHRBP extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserHRBPId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmHRBPId")
    private HRBP hrbp;
}
