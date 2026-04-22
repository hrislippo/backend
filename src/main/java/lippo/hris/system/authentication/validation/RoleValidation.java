package lippo.hris.system.authentication.validation;

import lippo.hris.system.authentication.repository.RoleRepository;
import lippo.hris.system.authentication.service.RoleService;
import lippo.hris.system.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleValidation {

    @Autowired
    RoleRepository roleRepository;

    public void duplicateRole(String roleName){
        if(roleRepository.findByRole("ROLE_"+roleName).isPresent()){
            throw new ConflictException("Role already exists");
        };
    }
}
