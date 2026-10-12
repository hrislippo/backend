package lippo.hris.system.workflowengine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "WMDocType", schema = "dbo")
public class WorkflowDocType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WmDocTypeId")
    private Long id;

    @Column(name = "WmDocTypeCode")
    private String code;

    @Column(name = "WmDocTypeName")
    private String name;
}
