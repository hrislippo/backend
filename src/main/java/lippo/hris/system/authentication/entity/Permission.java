package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "URMPermission", schema = "dbo")
public class Permission extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PermissionId")
    private Long id;

    @Column(name = "PermissionCode")
    private String code;

    @Column(name = "PermissionName")
    private String name;
}
