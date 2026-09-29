package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPoolPerf", schema = "dbo")
public class TalentPoolPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolPerfId")
    private Long id;

    @Column(name = "TlTalentPoolPerfName")
    private String name;
}
