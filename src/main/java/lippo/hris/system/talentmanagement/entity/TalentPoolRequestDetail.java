package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPoolReqDt", schema = "dbo")
public class TalentPoolRequestDetail extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolReqDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolReqHdId")
    private TalentPoolRequest header;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolReqDtTalent")
    private TalentPool talent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolReqDtReadiness")
    private TalentPoolReadiness readiness;
}
