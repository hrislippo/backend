package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMHRBP", schema = "dbo")
public class HRBP extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmHRBPId")
    private Long id;

    @Column(name = "RcmHRBPCode")
    private String code;

    @Column(name = "RcmHRBPName")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmBsUnitId", nullable = false)
    private BusinessUnit businessUnit;
}
