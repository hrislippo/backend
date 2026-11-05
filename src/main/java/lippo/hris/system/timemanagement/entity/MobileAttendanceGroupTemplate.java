package lippo.hris.system.timemanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lippo.hris.system.organization.entity.HRBP;
import lombok.Data;

@Data
@Entity
@Table(name = "TMMobileAttendGrpTemp", schema = "dbo")
public class MobileAttendanceGroupTemplate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MobileAttendGrpTempId")
    private Long id;

    @Column(name = "MobileAttendGrpTempCode")
    private String code;

    @Column(name = "MobileAttendGrpTempName")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RcmHRBPId")
    private HRBP hrbp;
}
