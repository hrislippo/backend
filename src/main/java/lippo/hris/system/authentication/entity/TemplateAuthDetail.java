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

    @Column(name = "TempAuthDtCompCode")
    private String companyCode;

    @Column(name = "TempAuthDtCompName")
    private String companyName;

    @Column(name = "TempAuthDtOrgCode")
    private String organizationCode;

    @Column(name = "TempAuthDtOrgName")
    private String organizationName;

    @Column(name = "TempAuthDtLocCode")
    private String locationCode;

    @Column(name = "TempAuthDtLocName")
    private String locationName;

    @Column(name = "TempAuthDtPosCode")
    private String positionCode;

    @Column(name = "TempAuthDtPosName")
    private String positionName;

    @Column(name = "TempAuthDtJobLvlCode")
    private String jobLevelCode;

    @Column(name = "TempAuthDtJobLvlName")
    private String jobLevelName;

    @Column(name = "TempAuthDtJobTtlCode")
    private String jobTitleCode;

    @Column(name = "TempAuthDtJobTtlName")
    private String jobTitleName;
}
