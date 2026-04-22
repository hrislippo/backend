package lippo.hris.system.authentication.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleRequest {

    private String username;
    private List<String> roles;
}
