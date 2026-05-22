package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RCMEmpRequest", schema = "dbo")
public class EmployeeRequestForm extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqId")
    private Long id;

    @Column(name = "EmpReqCode")
    private String code;

    @Column(name = "EmpReqName")
    private String name;

    @Column(name = "EmpReqStartDate")
    private LocalDate startDate;

    @Column(name = "EmpReqExpDate")
    private LocalDate expDate;

    @Column(name = "EmpReqNum")
    private Integer requestNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmBsUnitId")
    private BusinessUnit businessUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmHRBPId")
    private HRBP hrbp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmTempId")
    private RecruitmentTemplate recruitmentTemplate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmLvlTempId")
    private RecruitmentLevelTemplate recruitmentLevelTemplate;

    @Column(name = "EmpReqStatus")
    private String status = EmployeeRequestFormStatus.IN_PROGRESS.toString();
}
