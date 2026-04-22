package lippo.hris.system.emailengine.request;

import lombok.Data;
import java.util.Map;

@Data
public class EmailProcessReq {
    private String templateName;
    private String email;
    private Map<String, Object> parameter;
}
