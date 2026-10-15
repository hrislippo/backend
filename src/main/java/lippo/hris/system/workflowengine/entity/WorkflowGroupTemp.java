package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMGroupTemp", schema = "dbo")
public class WorkflowGroupTemp extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmGroupTempId")
    private Long id;

    @Column(name = "WmGroupTempCode")
    private String code;

    @Column(name = "WmGroupTempName")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmDocTypeId")
    private WorkflowDocType workflowDocType;

    @Column(name = "WmGroupTempSeq")
    private Integer sequence;

    @Column(name = "WmGroupTempActive")
    private Boolean active;

    @Column(name = "WmGroupTempFgComp")
    private Boolean flagCompany;

    @Column(name = "WmGroupTempFgOrg")
    private Boolean flagOrganization;

    @Column(name = "WmGroupTempFgJobLvl")
    private Boolean flagJobLevel;

    @Column(name = "WmGroupTempFgJobTtl")
    private Boolean flagJobTitle;

    @Column(name = "WmGroupTempFgLoc")
    private Boolean flagLocation;

    @Column(name = "WmGroupTempFgPos")
    private Boolean flagPosition;

    @Column(name = "WmGroupTempFgEmp")
    private Boolean flagEmployee;
}
