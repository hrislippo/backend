package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanSocMed", schema = "dbo")
public class CandidateSocialMedia extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanSocMedId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId", nullable = false)
    private Candidate candidate;

    @Column(name = "CanSocMedLink", nullable = false)
    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanSocMedMsId", nullable = false)
    private CandidateSocialMediaMaster type;
}
