package lippo.hris.system.entity;

import jakarta.persistence.*;
import lippo.hris.system.utility.EncryptedLongConverter;
import lombok.Data;

@Data
@Entity
@Table(name = "EncryptTest", schema = "dbo")
public class EncryptTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EncryptTestId")
    private Long id;

    @Convert(converter = EncryptedLongConverter.class)
    @Column(name = "EncryptTestAmount")
    private Long amount;
}
