package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMEmployType", schema = "dbo")
public class EmploymentType extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmEmployTypeId")
    private Long id;

    @Column(name = "RcmEmployTypeCode")
    private String code;

    @Column(name = "RcmEmployTypeName")
    private String name;
}
