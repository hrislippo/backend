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
    private String status = EmployeeRequestFormStatus.OPEN.toString();

    @Column(name = "EmpReqHireName")
    private String hiringName;

    @Column(name = "EmpReqHirePosName")
    private String hiringPositionName;

    @Column(name = "EmpReqHireCompName")
    private String hiringCompanyName;

    @Column(name = "EmpReqHireDivName")
    private String hiringDivisionName;

    @Column(name = "EmpReqReportTo")
    private String reportTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmEmployTypeId")
    private EmploymentType employmentType;

    @Column(name = "EmpReqRequisitionType")
    private String requisitionType;

    @Column(name = "EmpReqReplacementEmp")
    private String replacementEmployee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RCMPosLevelId")
    private PositionLevel positionLevel;

    @Column(name = "EmpReqWorkDays")
    private Integer workingDays;

    @Lob
    @Column(name = "EmpReqJobDesc", columnDefinition = "NVARCHAR(MAX)")
    private String jobDescription;

    @Column(name = "EmpReqEduBackground")
    private String educationBackground;

    @Column(name = "EmpReqMinExp")
    private String minimumExperience;

    @Lob
    @Column(name = "EmpReqSkillReq", columnDefinition = "NVARCHAR(MAX)")
    private String skillRequirement;
}
