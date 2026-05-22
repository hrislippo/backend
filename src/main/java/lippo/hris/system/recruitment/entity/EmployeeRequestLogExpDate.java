package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RCMEmpReqLogExpDate", schema = "dbo")
public class EmployeeRequestLogExpDate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqLogExpDateId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqId")
    private EmployeeRequestForm employeeRequestForm;

    @Column(name = "EmpReqLogExpDateOld")
    private LocalDate oldValue;

    @Column(name = "EmpReqLogExpDateNew")
    private LocalDate newValue;
}