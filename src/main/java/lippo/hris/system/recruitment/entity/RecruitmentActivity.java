package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMACTActivity", schema = "dbo")
public class RecruitmentActivity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmActId")
    private Long id;

    @Column(name = "RcmActCode")
    private String code;

    @Column(name = "RcmActName")
    private String name;

    @Column(name = "RcmActGrp")
    private String group;

    @Column(name = "RcmActFgInterview")
    private Boolean flagInterview = false;

    @Column(name = "RcmActFgEmail")
    private Boolean flagEmail = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmailTempId")
    private EmailTemplate emailTemplate;
}
