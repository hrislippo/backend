package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanPublish", schema = "dbo")
public class CandidatePublication extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanPublishId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId")
    private Candidate candidate;

    @Column(name = "CanPublish", nullable = false)
    private String publication;
}
