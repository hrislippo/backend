package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lippo.hris.system.recruitment.entity.CandidatePool;
import lombok.Data;

@Data
@Entity
@Table(name = "URMUserCanPool", schema = "dbo")
public class UserCandidatePool extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserCanPoolId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmCanPoolId")
    private CandidatePool candidatePool;
}
