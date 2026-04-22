package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanSocMedMs", schema = "dbo")
public class CandidateSocialMediaMaster extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanSocMedMsId")
    private Long id;

    @Column(name = "CanSocMedType", nullable = false)
    private String type;
}
