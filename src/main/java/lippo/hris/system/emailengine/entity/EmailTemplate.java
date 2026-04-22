package lippo.hris.system.emailengine.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "EmailTemplate", schema = "dbo")
public class EmailTemplate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmailTempId")
    private Long id;

    @Column(name = "EmailTempCode")
    private String code;

    @Column(name = "EmailTempName")
    private String name;

    @Column(name = "EmailTempSubject")
    private String subject;

    @Lob
    @Column(name = "EmailTempContentHtml", columnDefinition = "NVARCHAR(MAX)")
    private String contentHtml;

    @Lob
    @Column(name = "EmailTempDesignJson", columnDefinition = "NVARCHAR(MAX)")
    private String designJson;
}
