package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMCANCanContact", schema = "dbo")
public class CandidateContact extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CanContactId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanId", nullable = false)
    private Candidate candidate;

    @Column(name = "CanContact", nullable = false)
    private String contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CanContactMsId", nullable = false)
    private CandidateContactMaster type;
}
