package lippo.hris.system.authentication.request;

import lombok.Data;

import java.util.List;

@Data
public class UserTempAuthReq {
    private String username;
    private List<Long> tempAuthId;
}
