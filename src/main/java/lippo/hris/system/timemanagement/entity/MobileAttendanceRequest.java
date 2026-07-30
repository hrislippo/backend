package lippo.hris.system.timemanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "TMMobileAttendReq", schema = "dbo")
public class MobileAttendanceRequest extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TmMobileAttendReqId")
    private Long id;

    @Column(name = "TmMobileAttendReqEmp")
    private String employee;

    @Column(name = "TmMobileAttendReqTempCode")
    private String templateCode;

    @Column(name = "TmMobileAttendReqStartDate")
    private LocalDate startDate;

    @Column(name = "TmMobileAttendReqEndDate")
    private LocalDate endDate;

    @Column(name = "TmMobileAttendReqDesc")
    private String description;

    @Column(name = "TmMobileAttendReqExist")
    private Boolean exist = false;
}
