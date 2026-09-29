package lippo.hris.system.talentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TLTalentPoolPotent", schema = "dbo")
public class TalentPoolPotential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TlTalentPoolPotentId")
    private Long id;

    @Column(name = "TlTalentPoolPotentName")
    private String name;
}
