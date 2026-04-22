package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMEmpReqCandidate", schema = "dbo")
public class EmployeeRequestCandidate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqCanId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqId", nullable = false)
    private EmployeeRequestForm employeeRequestForm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId", nullable = false)
    private Candidate candidate;
}
