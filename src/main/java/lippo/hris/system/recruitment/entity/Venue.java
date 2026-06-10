package lippo.hris.system.recruitment.entity;

import jakarta.persistence.*;
import lippo.hris.system.entity.Auditable;
import lombok.Data;

@Data
@Entity
@Table(name = "RCMVenue", schema = "dbo")
public class Venue extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RcmVenueId")
    private Long id;

    @Column(name = "RcmVenueCode")
    private String code;

    @Column(name = "RcmVenueName")
    private String name;
}
