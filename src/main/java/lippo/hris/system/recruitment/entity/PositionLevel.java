package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMPosLevel", schema = "dbo")
public class PositionLevel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmPosLevelId")
    private Long id;

    @Column(name = "RcmPosLevelCode")
    private String code;

    @Column(name = "RcmPosLevelName")
    private String name;
}
