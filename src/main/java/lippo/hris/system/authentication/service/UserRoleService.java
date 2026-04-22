package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.Role;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserRole;
import lippo.hris.system.authentication.mapper.UserRoleMapper;
import lippo.hris.system.authentication.repository.RoleRepository;
import lippo.hris.system.authentication.repository.UserRoleRepository;
import lippo.hris.system.authentication.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleMapper userRoleMapper;

    public void updateRole(RoleRequest roleRequest, User user) {
        List<String> existingRoles = userRoleRepository.findByUser(user.getUsername()).stream().toList();
        List<String> added = new ArrayList<>(roleRequest.getRoles());
        added.removeAll(existingRoles);
        List<String> removed = new ArrayList<>(existingRoles);
        removed.removeAll(roleRequest.getRoles());

        for(String addedRole: added) {
            UserRole userRole = userRoleMapper.toEntity(user, roleRepository.findByRole(addedRole).get());
            userRole.setId(null);
            userRoleRepository.save(userRole);
        }

        for(String removedRole: removed) {
            UserRole userRole = findByUserAndRole(user, roleRepository.findByRole(removedRole).get());
            userRoleRepository.delete(userRole);
        }
    }

    public List<String> getUserRoles(User user){
        return userRoleRepository.findByUser(user.getUsername()).stream().toList();
    }

    public UserRole findByUserAndRole(User user, Role role){
        Optional<UserRole> userRole = userRoleRepository.findByUserAndRole(user, role);
        return userRole.orElse(null);
    }
}
