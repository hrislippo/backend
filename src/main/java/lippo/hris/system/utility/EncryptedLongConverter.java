package lippo.hris.system.utility;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

@Converter
public class EncryptedLongConverter implements AttributeConverter<Long, String> {

    @Autowired
    EncryptionUtil encryptionUtil;

    @Override
    public String convertToDatabaseColumn(Long value) {

        if (value == null) {
            return null;
        }

        try {
            return encryptionUtil.encrypt(
                    String.valueOf(value)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long convertToEntityAttribute(String value) {

        if (value == null) {
            return null;
        }

        try {
            return Long.valueOf(
                    encryptionUtil.decrypt(value)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
