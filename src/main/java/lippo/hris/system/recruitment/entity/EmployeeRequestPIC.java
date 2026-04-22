package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMEmpReqPIC", schema = "dbo")
public class EmployeeRequestPIC extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqPICId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqId", nullable = false)
    private EmployeeRequestForm employeeRequestForm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;
}
