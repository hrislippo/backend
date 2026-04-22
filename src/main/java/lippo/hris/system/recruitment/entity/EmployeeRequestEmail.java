package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMEmpReqEmail", schema = "dbo")
public class EmployeeRequestEmail extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpReqEmailId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqCanActId")
    private EmployeeRequestCandidateActivity employeeRequestCandidateActivity;

    @Column(name = "EmpReqEmailTo")
    private String emailTo;

    @Column(name = "EmpReqEmailCc")
    private String emailCc;

    @Column(name = "EmpReqEmailBcc")
    private String emailBcc;

    @Column(name = "EmpReqEmailSubject")
    private String emailSubject;

    @Lob
    @Column(name = "EmpReqEmailBody", columnDefinition = "NVARCHAR(MAX)")
    private String emailBody;

    @Lob
    @Column(name = "EmpReqEmailAttachment", columnDefinition = "VARBINARY(MAX)")
    private byte[] attachment;

    @Column(name = "EmpReqEmailAttachmentName")
    private String attachmentName;

    @Column(name = "EmpReqEmailAttachmentType")
    private String attachmentType;
}
