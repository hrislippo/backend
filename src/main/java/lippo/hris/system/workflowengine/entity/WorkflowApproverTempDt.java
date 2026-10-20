package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMApproverTempDt", schema = "dbo")
public class WorkflowApproverTempDt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmApproverTempDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmApproverTempId")
    private WorkflowApproverTemp workflowApproverTemp;

    @Column(name = "WmApproverTempDtCompCode")
    private String companyCode;

    @Column(name = "WmApproverTempDtCompName")
    private String companyName;

    @Column(name = "WmApproverTempDtOrgCode")
    private String organizationCode;

    @Column(name = "WmApproverTempDtOrgName")
    private String organizationName;

    @Column(name = "WmApproverTempDtLocCode")
    private String locationCode;

    @Column(name = "WmApproverTempDtLocName")
    private String locationName;

    @Column(name = "WmApproverTempDtPosCode")
    private String positionCode;

    @Column(name = "WmApproverTempDtPosName")
    private String positionName;

    @Column(name = "WmApproverTempDtJobLvlCode")
    private String jobLevelCode;

    @Column(name = "WmApproverTempDtJobLvlName")
    private String jobLevelName;

    @Column(name = "WmApproverTempDtJobTtlCode")
    private String jobTitleCode;

    @Column(name = "WmApproverTempDtJobTtlName")
    private String jobTitleName;

    @Column(name = "WmApproverTempDtEmpNik")
    private String employeeNik;

    @Column(name = "WmApproverTempDtEmpName")
    private String employeeName;
}
