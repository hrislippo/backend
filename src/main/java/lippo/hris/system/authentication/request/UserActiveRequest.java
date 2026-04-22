package lippo.hris.system.authentication.request;

import lombok.Data;

@Data
public class UserActiveRequest {

    private String username;
    private Boolean active;
}
