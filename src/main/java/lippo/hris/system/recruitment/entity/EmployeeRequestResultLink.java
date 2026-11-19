package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RCMEmpReqResLink", schema = "dbo")
public class EmployeeRequestResultLink extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqResLinkId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqResId")
    private EmployeeRequestResult employeeRequestResult;

    @Column(name = "EmpReqResLinkToken")
    private String token;

    @Column(name = "EmpReqResLinkStartDate")
    private LocalDate startDate;

    @Column(name = "EmpReqResLinkEndDate")
    private LocalDate endDate;
}
