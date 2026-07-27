package lippo.hris.system.authentication.request;

import lombok.Data;

@Data
public class UnlockRequest {
    private String username;
    private String reason;
}
