package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "URMTempAuthDt", schema = "dbo")
public class TemplateAuthDetail extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TempAuthDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TempAuthId")
    private TemplateAuth templateAuth;

    @Column(name = "TempAuthDtOrgCode")
    private String organizationCode;

    @Column(name = "TempAuthDtOrgName")
    private String organizationName;
}
