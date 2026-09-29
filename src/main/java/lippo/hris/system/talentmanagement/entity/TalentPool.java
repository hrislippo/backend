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

    @Column(name = "TlTalentPoolEmpNIK")
    private String employeeNIK;

    @Column(name = "TlTalentPoolEmpName")
    private String employeeName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolPerfId")
    private TalentPoolPerformance performance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TlTalentPoolPotentId")
    private TalentPoolPotential potential;
}
