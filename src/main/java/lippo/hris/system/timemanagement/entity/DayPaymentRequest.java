package lippo.hris.system.timemanagement.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "TMDayPaymentReq", schema = "dbo")
public class DayPaymentRequest extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TmDayPaymentReqId")
    private Long id;

    @Column(name = "TmDayPaymentReqEmp")
    private String employee;

    @Column(name = "TmDayPaymentReqCount")
    private Integer count;

    @Column(name = "TmDayPaymentReqDate")
    private LocalDate date;

    @Column(name = "TmDayPaymentReqExpDate")
    private LocalDate expiredDate;

    @Column(name = "TmDayPaymentReqDesc")
    private String description;
}
