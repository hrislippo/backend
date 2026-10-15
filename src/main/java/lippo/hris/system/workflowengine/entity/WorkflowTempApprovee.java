package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMTempApprovee", schema = "dbo")
public class WorkflowTempApprovee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmTempApproveeId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmTemplateId")
    private WorkflowTemp workflowTemp;

    @Column(name = "WmTempApproveeCompCode")
    private String companyCode;

    @Column(name = "WmTempApproveeCompName")
    private String companyName;

    @Column(name = "WmTempApproveeOrgCode")
    private String organizationCode;

    @Column(name = "WmTempApproveeOrgName")
    private String organizationName;

    @Column(name = "WmTempApproveeLocCode")
    private String locationCode;

    @Column(name = "WmTempApproveeLocName")
    private String locationName;

    @Column(name = "WmTempApproveePosCode")
    private String positionCode;

    @Column(name = "WmTempApproveePosName")
    private String positionName;

    @Column(name = "WmTempApproveeJobLvlCode")
    private String jobLevelCode;

    @Column(name = "WmTempApproveeJobLvlName")
    private String jobLevelName;

    @Column(name = "WmTempApproveeJobTtlCode")
    private String jobTitleCode;

    @Column(name = "WmTempApproveeJobTtlName")
    private String jobTitleName;

    @Column(name = "WmTempApproveeEmpNik")
    private String employeeNik;

    @Column(name = "WmTempApproveeEmpName")
    private String employeeName;
}
