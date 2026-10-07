package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "URMTempAuth", schema = "dbo")
public class TemplateAuth extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TempAuthId")
    private Long id;

    @Column(name = "TempAuthCode")
    private String code;

    @Column(name = "TempAuthName")
    private String name;
}
