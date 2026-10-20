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
    @JoinColumn(name = "WmApproverTempId")
    private WorkflowApproverTemp workflowApproverTemp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmTemplateId")
    private WorkflowTemp workflowTemp;

    @Column(name = "WmTempApproverLvl")
    private Integer level;

    @Column(name = "WmTempApproverSeq")
    private Integer sequence;
}
