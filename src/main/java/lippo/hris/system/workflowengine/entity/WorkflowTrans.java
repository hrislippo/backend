package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMTransaction", schema = "dbo")
public class WorkflowTrans extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmTransactionId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmDocTypeId")
    private WorkflowDocType workflowDocType;

    @Column(name = "WmTransDocId")
    private Long docId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    private User approvee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmStatusId")
    private WorkflowStatus workflowStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmTemplateId")
    private WorkflowTemp workflowTemp;
}
