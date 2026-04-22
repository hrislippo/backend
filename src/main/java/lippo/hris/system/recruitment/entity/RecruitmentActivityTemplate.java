package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;


@Data
@Entity
@Table(name = "RCMACTActivityTemplate", schema = "dbo")
public class RecruitmentActivityTemplate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmActTempId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmTempId", nullable = false)
    private RecruitmentTemplate template;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmActId", nullable = false)
    private RecruitmentActivity activity;

    @Column(name = "RcmActOrder")
    private Integer order;
}
