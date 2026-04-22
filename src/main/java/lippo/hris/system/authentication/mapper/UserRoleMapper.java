package lippo.hris.system.authentication.mapper;

import lippo.hris.system.authentication.entity.Role;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    @Mapping(source="user", target="user")
    @Mapping(source="role", target="role")
    @Mapping(target = "id", ignore = true)
    UserRole toEntity(User user, Role role);
}
