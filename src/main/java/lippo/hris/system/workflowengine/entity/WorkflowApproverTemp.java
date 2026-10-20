package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMApproverTemp", schema = "dbo")
public class WorkflowApproverTemp extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmApproverTempId")
    private Long id;

    @Column(name = "WmApproverTempCode")
    private String code;

    @Column(name = "WmApproverTempName")
    private String name;

    @Column(name = "WmApproverTempActive")
    private Boolean active;

    @Column(name = "WmApproverTempFgComp")
    private Boolean flagCompany;

    @Column(name = "WmApproverTempFgOrg")
    private Boolean flagOrganization;

    @Column(name = "WmApproverTempFgJobLvl")
    private Boolean flagJobLevel;

    @Column(name = "WmApproverTempFgJobTtl")
    private Boolean flagJobTitle;

    @Column(name = "WmApproverTempFgLoc")
    private Boolean flagLocation;

    @Column(name = "WmApproverTempFgPos")
    private Boolean flagPosition;

    @Column(name = "WmApproverTempFgEmp")
    private Boolean flagEmployee;
}
