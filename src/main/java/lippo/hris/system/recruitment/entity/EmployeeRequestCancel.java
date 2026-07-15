package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMEmpReqCancel", schema = "dbo")
public class EmployeeRequestCancel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqCancelId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqId")
    private EmployeeRequestForm employeeRequestForm;

    @Column(name = "EmpReqCancelReason")
    private String reason;
}
