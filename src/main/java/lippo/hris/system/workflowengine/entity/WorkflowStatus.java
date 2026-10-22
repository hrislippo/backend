package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "WMStatus", schema = "dbo")
public class WorkflowStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmStatusId")
    private Long id;

    @Column(name = "WmStatusDesc")
    private String description;
}
