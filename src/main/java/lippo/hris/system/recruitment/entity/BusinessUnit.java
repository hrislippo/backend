package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMBusinessUnit", schema = "dbo")
public class BusinessUnit extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmBsUnitId")
    private Long id;

    @Column(name = "RcmBsUnitCode")
    private String code;

    @Column(name = "RcmBsUnitName")
    private String name;
}
