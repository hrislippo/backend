package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMACTTemplate", schema = "dbo")
public class RecruitmentTemplate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmTempId")
    private Long id;

    @Column(name = "RcmTempCode")
    private String code;

    @Column(name = "RcmTempName")
    private String name;
}
