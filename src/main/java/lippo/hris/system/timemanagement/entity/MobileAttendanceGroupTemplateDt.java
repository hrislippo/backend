package lippo.hris.system.timemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TMMobileAttendGrpTempDt", schema = "dbo")
public class MobileAttendanceGroupTemplateDt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MobileAttendGrpTempDtId")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MobileAttendGrpTempId")
    private MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate;

    @Column(name = "MobileAttendTempCode")
    private String templateCode;

    @Column(name = "MobileAttendTempName")
    private String templateName;
}
