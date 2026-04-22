package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RCMEmpReqCanActivity", schema = "dbo")
public class EmployeeRequestCandidateActivity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqCanActId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqCanId", nullable = false)
    private EmployeeRequestCandidate employeeRequestCandidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmActId", nullable = false)
    private RecruitmentActivity recruitmentActivity;

    @Column(name = "EmpReqCanActStatus")
    private String status;

    @Lob
    @Column(name = "EmpReqCanActFile", columnDefinition = "VARBINARY(MAX)")
    private byte[] attachment;

    @Column(name = "EmpReqCanActFileName")
    private String fileName;

    @Column(name = "EmpReqCanActFileType")
    private String fileType;

    @Lob
    @Column(name = "EmpReqCanActNotes", columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    @Column(name = "EmpReqCanActSchedule")
    private LocalDateTime schedule;
}
