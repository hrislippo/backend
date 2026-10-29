package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "WMTransApprover", schema = "dbo")
public class WorkflowTransApprover extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmTransApproverId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmTransactionId")
    private WorkflowTrans workflowTrans;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    private User approver;

    @Column(name = "WmTransApproverLvl")
    private Integer level;

    @Column(name = "WmTransApproverSeq")
    private Integer sequence;

    @Column(name = "WmTransApproverFgApprove")
    private Boolean approve;

    @Column(name = "WmTransApproverDesc")
    private String description;

    @Column(name = "WmTransApproverActionTime")
    private LocalDateTime actionTime;
}
