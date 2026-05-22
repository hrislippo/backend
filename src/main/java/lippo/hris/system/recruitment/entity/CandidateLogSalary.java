package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanLogSalary", schema = "dbo")
public class CandidateLogSalary extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanLogSalaryId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId")
    private Candidate candidate;

    @Column(name = "CanLogSalaryOld")
    private Long oldValue;

    @Column(name = "CanLogSalaryNew")
    private Long newValue;

    @Column(name = "CanLogSalaryField")
    private String field;
}
