package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPoolReqHd", schema = "dbo")
public class TalentPoolRequest extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolReqHdId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolReqHdTalent")
    private TalentPool talent;

    @Column(name = "TlTalentPoolReqHdPosCode")
    private String positionCode;

    @Column(name = "TlTalentPoolReqHdPosName")
    private String positionName;

    @Column(name = "TlTalentPoolReqHdPilarName")
    private String pilarName;

    @Column(name = "TlTalentPoolReqHdBuName")
    private String buName;
}

