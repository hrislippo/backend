package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCanPoolMbr", schema = "dbo")
public class CandidatePoolMember extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmCanPoolMbrId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmCanPoolId")
    private CandidatePool candidatePool;

    @Column(name = "RcmCanPoolMbrMain")
    private Boolean main;
}
