package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanLang", schema = "dbo")
public class CandidateLanguage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanLangId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId")
    private Candidate candidate;

    @Column(name = "CanLangName", nullable = false)
    private String languageName;

    @Column(name = "CanLangProficiency")
    private String proficiency;
}
