package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanContactMs", schema = "dbo")
public class CandidateContactMaster extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanContactMsId")
    private Long id;

    @Column(name = "CanContactType", nullable = false)
    private String type;
}
