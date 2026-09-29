package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPoolLog", schema = "dbo")
public class TalentPoolLog extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolLogId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolId")
    private TalentPool talentPool;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolLogOldPerfId")
    private TalentPoolPerformance oldPerformance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolLogOldPotentId")
    private TalentPoolPotential oldPotential;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolLogNewPerfId")
    private TalentPoolPerformance newPerformance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolLogNewPotentId")
    private TalentPoolPotential newPotential;

    @Column(name = "TlTalentPoolLogReason")
    private String reason;
}
