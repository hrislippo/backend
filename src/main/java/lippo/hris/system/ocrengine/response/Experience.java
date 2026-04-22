package lippo.hris.system.ocrengine.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Experience {
    private String companyName;
    private String positionName;
    private String duration;
    private String locationName;
    private String description;
    private String startYear;
    private String startMonth;
    private String endMonth;
    private String endYear;
}
