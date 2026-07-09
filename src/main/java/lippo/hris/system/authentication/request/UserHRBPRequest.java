package lippo.hris.system.authentication.request;

import lombok.Data;

import java.util.List;

@Data
public class UserHRBPRequest {

    private String username;
    private List<String> hrbp;
}
