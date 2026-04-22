package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RCMCANCanInterview", schema = "dbo")
public class Interview extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanInterviewId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EmpReqCanActId")
    private EmployeeRequestCandidateActivity employeeRequestCandidateActivity;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "EndTime")
    private LocalDateTime endTime;

    @Lob
    @Column(name = "CanInterviewNotes", columnDefinition = "NVARCHAR(MAX)")
    private String notes;

    @Column(name = "CanFgShortlist")
    private Boolean flagShortlist;
}
