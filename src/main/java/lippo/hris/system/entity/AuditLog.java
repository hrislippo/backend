package lippo.hris.system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "AuditLog", schema = "dbo")
public class AuditLog extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditLogId")
    private Long id;

    @Column(name = "AuditLogAction")
    private String action;
}
