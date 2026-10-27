package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMTempApprover", schema = "dbo")
public class WorkflowTempApprover extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmTempApproverId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmTemplateId")
    private WorkflowTemp workflowTemp;

    @Column(name = "WmTempApproverLvl")
    private Integer level;

    @Column(name = "WmTempApproverCompCode")
    private String companyCode;

    @Column(name = "WmTempApproverCompName")
    private String companyName;

    @Column(name = "WmTempApproverOrgCode")
    private String organizationCode;

    @Column(name = "WmTempApproverOrgName")
    private String organizationName;

    @Column(name = "WmTempApproverLocCode")
    private String locationCode;

    @Column(name = "WmTempApproverLocName")
    private String locationName;

    @Column(name = "WmTempApproverPosCode")
    private String positionCode;

    @Column(name = "WmTempApproverPosName")
    private String positionName;

    @Column(name = "WmTempApproverJobLvlCode")
    private String jobLevelCode;

    @Column(name = "WmTempApproverJobLvlName")
    private String jobLevelName;

    @Column(name = "WmTempApproverJobTtlCode")
    private String jobTitleCode;

    @Column(name = "WmTempApproverJobTtlName")
    private String jobTitleName;

    @Column(name = "WmTempApproverEmpNik")
    private String employeeNik;

    @Column(name = "WmTempApproverEmpName")
    private String employeeName;

    @Column(name = "WmTempApproverOrgChartCode")
    private String orgChartCode;

    @Column(name = "WmTempApproverOrgChartName")
    private String orgChartName;
}
