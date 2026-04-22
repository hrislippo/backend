package lippo.hris.system.authentication.validation;

import lippo.hris.system.authentication.request.RoleRequest;
import lippo.hris.system.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidation {

    public void noRole(RoleRequest roleRequest){
        if(roleRequest.getRoles().isEmpty()){
            throw new BadRequestException("Roles cannot be empty");
        }
    }
}
