package lippo.hris.system.ocrengine.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Education {
    private String institutionName;
    private String degreeName;
    private String duration;
    private String startYear;
    private String startMonth;
    private String endMonth;
    private String endYear;
}
