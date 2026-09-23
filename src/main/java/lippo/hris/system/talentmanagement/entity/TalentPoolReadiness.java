package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPoolReadinessMs", schema = "dbo")
public class TalentPoolReadiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolReadinessMsId")
    private Long id;

    @Column(name = "TlTalentPoolReadinessName")
    private String name;
}
