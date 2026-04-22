package lippo.hris.system.authentication.mapper;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.request.LoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(LoginRequest loginRequest);
}
