package lippo.hris.system.authentication.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "URMMenu", schema = "dbo")
public class Menu extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuId")
    private Long id;

    @Column(name = "MenuIcon")
    private String icon;

    @Column(name = "MenuName")
    private String name;

    @Column(name = "MenuPath")
    private String path;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MenuParentId")
    private Menu parent;

    @Column(name = "MenuOrder")
    private Integer order;
}
