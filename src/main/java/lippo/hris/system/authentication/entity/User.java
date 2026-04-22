package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "URMUser", schema = "dbo")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long id;

    @Column(name = "UserName", unique = true, nullable = false)
    private String username;

    @Column(name = "UserRealName")
    private String name;

    @Column(name = "UserPassword", nullable = false)
    private String password;

    @Column(name = "UserActive", nullable = false)
    private Boolean active = true;
}
