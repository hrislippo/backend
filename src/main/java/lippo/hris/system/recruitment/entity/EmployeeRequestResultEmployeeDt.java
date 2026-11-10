package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "RCMEmpReqResEmpDt", schema = "dbo")
public class EmployeeRequestResultEmployeeDt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqResEmpDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqResId")
    private EmployeeRequestResult employeeRequestResult;

    @Column(name = "EmpReqResCompCode")
    private String companyCode;

    @Column(name = "EmpReqResCompName")
    private String companyName;

    @Column(name = "EmpReqResOrgCode")
    private String organizationCode;

    @Column(name = "EmpReqResOrgName")
    private String organizationName;

    @Column(name = "EmpReqResJobLvlCode")
    private String jobLevelCode;

    @Column(name = "EmpReqResJobLvlName")
    private String jobLevelName;

    @Column(name = "EmpReqResJobTtlCode")
    private String jobTitleCode;

    @Column(name = "EmpReqResJobTtlName")
    private String jobTitleName;

    @Column(name = "EmpReqResLocCode")
    private String locationCode;

    @Column(name = "EmpReqResLocName")
    private String locationName;

    @Column(name = "EmpReqResPosCode")
    private String positionCode;

    @Column(name = "EmpReqResPosName")
    private String positionName;

    @Column(name = "EmpReqResCostCenterCode")
    private String costCenterCode;

    @Column(name = "EmpReqResCostCenterName")
    private String costCenterName;

    @Column(name = "EmpReqResEmployType")
    private String employmentType;

    @Column(name = "EmpReqResJobStartDate")
    private LocalDate jobStartDate;

    @Column(name = "EmpReqResJobEndDate")
    private LocalDate jobEndDate;

    @Column(name = "EmpReqResLocRecruitCode")
    private String locationRecruitCode;

    @Column(name = "EmpReqResLocRecruitName")
    private String locationRecruitName;

    @Column(name = "EmpReqResEffDate")
    private LocalDate effectiveDate;

    @Column(name = "EmpReqResStartDate")
    private LocalDate startDate;

    @Column(name = "EmpReqResJoinDate")
    private LocalDate joinDate;

    @Column(name = "EmpReqResSignDate")
    private LocalDate signDate;
}
