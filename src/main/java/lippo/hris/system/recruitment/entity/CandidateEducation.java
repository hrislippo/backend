package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanEdu", schema = "dbo")
public class CandidateEducation extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanEduId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId")
    private Candidate candidate;

    @Column(nullable = false, name = "CanInstituteName")
    private String institutionName;

    @Column(name = "CanDegreeName")
    private String degreeName;

    @Column(name = "CanEduStartYear")
    private String startYear;

    @Column(name = "CanEduStartMonth")
    private String startMonth;

    @Column(name = "CanEduEndYear")
    private String endYear;

    @Column(name = "CanEduEndMonth")
    private String endMonth;
}
