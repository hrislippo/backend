package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "WMGroupTempDt", schema = "dbo")
public class WorkflowGroupTempDt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmGroupTempDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmGroupTempId")
    private WorkflowGroupTemp workflowGroupTemp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WmTemplateId")
    private WorkflowTemp workflowTemp;
}
