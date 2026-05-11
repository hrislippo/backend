package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanDoc", schema = "dbo")
public class CandidateDocument extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanDocId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId", nullable = false)
    private Candidate candidate;

    @Lob
    @Column(name = "CanDoc", columnDefinition = "VARBINARY(MAX)")
    private byte[] document;

    @Column(name = "CanDocName")
    private String documentName;

    @Column(name = "CanDocType")
    private String documentType;
}
