package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMEmpReqResPsnDt", schema = "dbo")
public class EmployeeRequestResultPersonalDt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqResPsnDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqResId")
    private EmployeeRequestResult employeeRequestResult;

    @Column(name = "EmpReqResKtpNum")
    private String ktpNumber;

    @Column(name = "EmpReqResGender")
    private String gender;

    @Column(name = "EmpReqResMrtStatus")
    private String maritalStatus;

    @Column(name = "EmpReqResReligion")
    private String religion;
}
