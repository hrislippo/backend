package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanExp", schema = "dbo")
public class CandidateExperience extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanExpId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId")
    private Candidate candidate;

    @Column(name = "CanExpCompName", nullable = false)
    private String companyName;

    @Column(name = "CanExpPosName", nullable = false)
    private String positionName;

    @Column(name = "CanExpLocName")
    private String locationName;

    @Column(name = "CanExpStartYear")
    private String startYear;

    @Column(name = "CanExpStartMonth")
    private String startMonth;

    @Column(name = "CanExpEndYear")
    private String endYear;

    @Column(name = "CanExpEndMonth")
    private String endMonth;

    @Lob
    @Column(name = "CanExpDesc")
    private String description;
}
