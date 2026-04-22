package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMLvlTemplate", schema = "dbo")
public class RecruitmentLevelTemplate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmLvlTempId")
    private Long id;

    @Column(name = "RcmLvlTempCode")
    private String code;

    @Column(name = "RcmLvlTempName")
    private String name;

    @Column(name = "RcmLvlTempDays")
    private Integer days;
}
