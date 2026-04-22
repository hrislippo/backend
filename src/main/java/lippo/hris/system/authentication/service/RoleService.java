package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.Role;
import lippo.hris.system.authentication.entity.UserRole;
import lippo.hris.system.authentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void saveRole(String roleName){
        Role role = new Role();
        role.setRole("ROLE_"+roleName);
        roleRepository.save(role);
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
}
