package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPool", schema = "dbo")
public class TalentPool extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolId")
    private Long id;

    @Column(name = "TlTalentPoolPosCode")
    private String positionCode;

    @Column(name = "TlTalentPoolPosName")
    private String positionName;

    @Column(name = "TlTalentPoolEmpNIK")
    private String employeeNIK;

    @Column(name = "TlTalentPoolPotential")
    private Integer potential;

    @Column(name = "TlTalentPoolPerformance")
    private Integer performance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolReadinessMsId")
    private TalentPoolReadiness readiness;
}

