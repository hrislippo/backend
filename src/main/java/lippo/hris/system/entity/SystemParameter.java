package lippo.hris.system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SystemParameter", schema = "dbo")
public class SystemParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SysParamId")
    private Long id;

    @Column(name = "SysParamKey")
    private String key;

    @Column(name = "SysParamValue")
    private String value;

}
