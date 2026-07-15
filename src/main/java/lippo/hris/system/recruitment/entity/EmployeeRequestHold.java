package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RCMEmpReqHold", schema = "dbo")
public class EmployeeRequestHold extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqHoldId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqId")
    private EmployeeRequestForm employeeRequestForm;

    @Column(name = "EmpReqHoldOldStartDate")
    private LocalDate oldStartDate;

    @Column(name = "EmpReqHoldOldExpiryDate")
    private LocalDate oldExpiryDate;

    @Column(name = "EmpReqHoldStartDate")
    private LocalDate startDate;

    @Column(name = "EmpReqHoldEndDate")
    private LocalDate endDate;

    @Column(name = "EmpReqHoldReason")
    private String reason;
}
