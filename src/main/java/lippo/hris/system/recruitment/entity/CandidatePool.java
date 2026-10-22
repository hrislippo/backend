package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCanPool", schema = "dbo")
public class CandidatePool extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmCanPoolId")
    private Long id;

    @Column(name = "RcmCanPoolCode")
    private String code;

    @Column(name = "RcmCanPoolName")
    private String name;
}
